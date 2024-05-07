package com.izertis.SimpleShared.core.service;

import com.izertis.SimpleShared.core.domain.Expense;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.FriendNotFoundException;
import com.izertis.SimpleShared.core.domain.exception.NegativeExpenseAmountException;
import com.izertis.SimpleShared.primaryAdapter.request.ExpenseRequest;
import com.izertis.SimpleShared.secondaryAdapter.repository.ExpenseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ExpenseServiceTest {
    private final ExpenseRepository repository = mock(ExpenseRepository.class);
    private final FriendService friendService = mock(FriendService.class);
    private final ExpenseService sut = new ExpenseService(repository, friendService);

    @Test
    public void shouldReturnAllExpenses() throws EmptyExpenseListException {
        //GIVEN:
        Friend friend = new Friend(1, "Juan");
        List<Expense> expected = new ArrayList<>();
        expected.add(new Expense(1, friend, 10.00, "Test", "2024-04-09 16:00:00"));
        when(repository.findExpenses()).thenReturn(expected);

        //WHEN:
        List<Expense> result = sut.findExpenses();

        //THEN:
        Assertions.assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getIdExpense(), result.get(i).getIdExpense());
            assertEquals(expected.get(i).getDescription(), result.get(i).getDescription());
            assertEquals(expected.get(i).getAmount(), result.get(i).getAmount());
            assertEquals(expected.get(i).getFriend(), result.get(i).getFriend());
        }
    }

    @Test
    public void shoulThrowExceptionWhenEmptyExpenses() throws EmptyExpenseListException {
        //GIVEN:
        List<Expense> training = new ArrayList<>();
        Mockito.when(repository.findExpenses()).thenReturn(training);

        //WHEN
        EmptyExpenseListException exception = assertThrows(EmptyExpenseListException.class, () -> sut.findExpenses());
        // THEN
        assertEquals("No expenses found", exception.getMessage());
    }

    @Test
    public void shouldAddExpense() throws EmptyFriendListException, FriendNotFoundException, NegativeExpenseAmountException {
        //GIVEN:
        ExpenseRequest request = new ExpenseRequest(10.00, "Test");
        Friend friend = new Friend(1, "Juan");
        Expense expected = new Expense(1, friend, 10.00, "Test", "2024-04-09 16:00:00");
        when(friendService.findFriendById(1)).thenReturn(friend);

        //WHEN:
        sut.addExpense(1, request);

        //THEN:
        Mockito.verify(repository).addExpense(any(Expense.class));
    }

    @Test
    public void shouldNotAddIfNegativeExpense(){
        //GIVEN:
        ExpenseRequest request = new ExpenseRequest(-20.00, "Train");

        //WHEN
        NegativeExpenseAmountException exception = assertThrows(NegativeExpenseAmountException.class, () -> sut.addExpense(1, request));
        // THEN
        assertEquals("Amount must be positive", exception.getMessage());

    }
}
