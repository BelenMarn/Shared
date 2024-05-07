package com.izertis.SimpleShared.secondaryAdapter.repository.integrationTest;

import com.izertis.SimpleShared.core.domain.Expense;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.NegativeExpenseAmountException;
import com.izertis.SimpleShared.secondaryAdapter.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MysqlExpenseRepositoryIT {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public MysqlExpenseRepositoryIT(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Test
    public void shouldFindAllExpenses() throws EmptyExpenseListException {
        assertThat(expenseRepository.findExpenses().size()).isGreaterThan(0);
    }

    @Test
    public void shouldSaveNewExpense() throws NegativeExpenseAmountException, EmptyExpenseListException {
        long id = 8000;
        Friend friend = new Friend(3, "Pedro");
        Expense expected = new Expense(id, friend, 20.00, "Test", "2024-04-10 12:30:00");

        expenseRepository.addExpense(expected);

        List<Expense> allExpenses = expenseRepository.findExpenses();
        assertTrue(allExpenses.contains(expected));

    }
}
