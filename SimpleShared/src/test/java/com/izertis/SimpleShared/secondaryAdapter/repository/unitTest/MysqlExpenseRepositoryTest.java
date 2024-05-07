package com.izertis.SimpleShared.secondaryAdapter.repository.unitTest;

import com.izertis.SimpleShared.core.domain.Expense;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.FriendNotFoundException;
import com.izertis.SimpleShared.core.domain.exception.NegativeExpenseAmountException;
import com.izertis.SimpleShared.secondaryAdapter.entity.ExpenseEntity;
import com.izertis.SimpleShared.secondaryAdapter.repository.FriendRepository;
import com.izertis.SimpleShared.secondaryAdapter.repository.MysqlExpenseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class MysqlExpenseRepositoryTest {
    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final FriendRepository friendRepository = Mockito.mock(FriendRepository.class);
    private final MysqlExpenseRepository sut = new MysqlExpenseRepository(jdbcTemplate, friendRepository);

    @Test
    public void shouldFindAllExpenses() throws FriendNotFoundException, EmptyExpenseListException {
        //GIVEN
        List<ExpenseEntity> training = new ArrayList<>();
        training.add(new ExpenseEntity(1, 1, 10.00, "Test", "2024-04-09 16:00:00"));

        when(friendRepository.findFriendById(anyLong()))
                .thenReturn(new Friend(1, "Friend to test"));

        when(jdbcTemplate.query(anyString(), isA(RowMapper.class)))
                .thenReturn(training);

        List<Expense> expected = new ArrayList<>();
        expected.add(new Expense(1, new Friend(1, "Friend to test"), 10.00, "Test", "2024-04-09 16:00:00"));

        //WHEN
        final List<Expense> result = sut.findExpenses();

        //THEN
        assertEquals(expected.size(), result.size());
        org.assertj.core.api.Assertions.assertThat(result).containsExactlyElementsOf(expected);

    }

    @Test
    public void shouldThrowExceptionWhenEmptyExpenses(){
        //GIVEN
        List<ExpenseEntity> expected = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), isA(RowMapper.class))).
                thenReturn(expected);
        //WHEN
        if(expected.isEmpty()){
            EmptyExpenseListException exception = assertThrows(EmptyExpenseListException.class, () -> sut.findExpenses());
            // THEN
            assertEquals("No expenses found", exception.getMessage());
        }
    }

    @Test
    public void shouldAddExpense() throws FriendNotFoundException, NegativeExpenseAmountException {
        //GIVEN
        Friend friend = new Friend(1, "Juan");
        Expense given = new Expense(1, friend, 10.00, "Test", "2024-04-09 16:00:00");
        when(friendRepository.findFriendById(anyLong())).thenReturn(friend);

        ExpenseEntity expected = new ExpenseEntity(1, 1, 10.00, "Test", "2024-04-09 16:00:00");
        //WHEN
        sut.addExpense(given);
        //THEN
        Mockito.verify(jdbcTemplate).update(anyString(),
                eq(given.getIdExpense()),
                eq(given.getFriend().getIdFriend()),
                eq(given.getAmount()),
                eq(given.getDescription()),
                anyString());
    }

    @Test
    public void shouldNotAddIfNegativeExpense(){
        //GIVEN:
        Friend friend = new Friend(1, "Juan");
        Expense given = new Expense(1, friend, 10.00, "Test", "2024-04-09 16:00:00");

        //WHEN:
        if (given.getAmount() <= 0) {
            NegativeExpenseAmountException exception = assertThrows(NegativeExpenseAmountException.class, () -> sut.addExpense(given));

            //THEN:
            Assertions.assertEquals("Amount must be positive", exception.getMessage());
        }
    }
}
