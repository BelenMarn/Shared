package com.izertis.SimpleShared.secondaryAdapter.repository;

import com.izertis.SimpleShared.core.domain.Expense;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.NegativeExpenseAmountException;

import java.util.List;

public interface ExpenseRepository {
    List<Expense> findExpenses() throws EmptyExpenseListException;
    void addExpense(Expense expense) throws NegativeExpenseAmountException;

}
