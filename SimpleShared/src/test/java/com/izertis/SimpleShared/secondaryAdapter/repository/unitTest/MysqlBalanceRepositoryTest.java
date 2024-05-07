package com.izertis.SimpleShared.secondaryAdapter.repository.unitTest;

import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.secondaryAdapter.repository.FriendRepository;
import com.izertis.SimpleShared.secondaryAdapter.repository.MysqlBalanceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class MysqlBalanceRepositoryTest {
    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final FriendRepository friendRepository = Mockito.mock(FriendRepository.class);
    private MysqlBalanceRepository sut =  new MysqlBalanceRepository(jdbcTemplate, friendRepository);


    @Test
    public void shouldReturnNumberOfFriends() throws EmptyExpenseListException {
        //GIVEN
        int numberExpected = 5;
        when(jdbcTemplate.queryForObject(anyString(), Mockito.eq(Integer.class))).thenReturn(numberExpected);
        //WHEN
        int numberGiven = sut.getNumberOfFriends();
        //THEN
        assertEquals(numberExpected, numberGiven);
    }

   @Test
    public void shouldReturnAverageSpending() throws EmptyExpenseListException {
        //GIVEN
        double amountExpected = 50.50;
        int numberOfFriends = 2;
        when(jdbcTemplate.queryForObject(anyString(), Mockito.eq(Integer.class))).thenReturn(numberOfFriends);
        when(jdbcTemplate.queryForObject(anyString(), Mockito.eq(Double.class))).thenReturn(amountExpected);
        //WHEN
        double average = sut.getAverageSpending();
        //THEN
        assertEquals(amountExpected / numberOfFriends, average);
    }

    @Test
    public void shouldReturnBalanceOfFriend(){
        //GIVEN
        long id = 1;
        double amount = 20.00;
        when(jdbcTemplate.queryForObject(anyString(), Mockito.eq(Double.class), Mockito.eq(id))).thenReturn(amount);
        //WHEN
        double total = sut.getBalanceOfFriend(id);
        //THEN
        assertEquals(amount, total);
    }
}
