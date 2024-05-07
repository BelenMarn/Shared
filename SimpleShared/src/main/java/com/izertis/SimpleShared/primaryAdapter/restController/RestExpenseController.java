package com.izertis.SimpleShared.primaryAdapter.restController;

import com.izertis.SimpleShared.core.domain.Expense;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.FriendNotFoundException;
import com.izertis.SimpleShared.core.domain.exception.NegativeExpenseAmountException;
import com.izertis.SimpleShared.core.service.ExpenseService;
import com.izertis.SimpleShared.primaryAdapter.request.ExpenseRequest;
import com.izertis.SimpleShared.primaryAdapter.respond.ExpenseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/expense")
public class RestExpenseController {
    private ExpenseService expenseService;

    @Autowired
    public RestExpenseController(ExpenseService expenseService) {this.expenseService = expenseService;}

    @GetMapping
    public List<ExpenseResponse> findExpenses() throws EmptyExpenseListException {
        final List<Expense> expenses = expenseService.findExpenses();
        return toExpensesRespond(expenses);
    }

    @PostMapping("/friend/{id}")
    public void addExpense(@PathVariable long id, @RequestBody ExpenseRequest request) throws NegativeExpenseAmountException, EmptyFriendListException, FriendNotFoundException {
        expenseService.addExpense(id, request);

    }

    private List<ExpenseResponse> toExpensesRespond(List<Expense> expenses){
        return expenses.stream()
                .map(expense -> new ExpenseResponse(
                        expense.getIdExpense(),
                        expense.getFriend().getIdFriend(),
                        expense.getAmount(),
                        expense.getDescription(),
                        expense.getExpenseDate()))
                .collect(Collectors.toList());
    }
}
