package com.izertis.SimpleShared.core.service;

import com.izertis.SimpleShared.core.domain.Expense;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.FriendNotFoundException;
import com.izertis.SimpleShared.core.domain.exception.NegativeExpenseAmountException;
import com.izertis.SimpleShared.primaryAdapter.request.ExpenseRequest;
import com.izertis.SimpleShared.secondaryAdapter.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;
    private FriendService friendService;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, FriendService friendService) {
        this.expenseRepository = expenseRepository;
        this.friendService = friendService;
    }

    public List<Expense> findExpenses() throws EmptyExpenseListException {
        final List<Expense> expenses = new ArrayList<Expense>();
        final Iterator<Expense> expenseIterator = expenseRepository.findExpenses().iterator();
        while (expenseIterator.hasNext()) {
            expenses.add(expenseIterator.next());
        }

        if(!expenses.isEmpty()){
            return expenses;
        }else{
            throw new EmptyExpenseListException("No expenses found");
        }
    }

    public void addExpense(long idFriend, ExpenseRequest request) throws FriendNotFoundException, NegativeExpenseAmountException {
        Friend friend = getFriend(idFriend);

        Expense expense = new Expense();
        expense.setIdExpense(generateExpenseId());
        expense.setFriend(friend);
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(generateDate());

        if(friend == null){
            throw new FriendNotFoundException("Friend not found");
        }

        if(expense.getAmount() <= 0){
            throw new NegativeExpenseAmountException("Amount must be positive");
        }else{
            expenseRepository.addExpense(expense);
        }
    }

    public Friend getFriend(long idFriend) throws FriendNotFoundException {
        Friend friend = friendService.findFriendById(idFriend);
        return friend;
    }

    public long generateExpenseId(){
        return new Date().getTime();
    }
    public String generateDate(){
        long currentDate = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(currentDate);
        String formatedDate = dateFormat.format(date);

        return formatedDate;
    }
}
