package com.izertis.SimpleShared.core.service;

import com.izertis.SimpleShared.core.domain.Balance;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.NoBalanceFoundException;
import com.izertis.SimpleShared.primaryAdapter.respond.BalanceResponse;
import com.izertis.SimpleShared.secondaryAdapter.repository.BalanceRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class BalanceServiceTest {
    private final BalanceRepository repository = Mockito.mock(BalanceRepository.class);
    private final BalanceService sut = new BalanceService(repository);

    @Test
    public void shouldGetFinalBalance() throws EmptyExpenseListException, NoBalanceFoundException, EmptyFriendListException {
        //GIVEN
        List<Balance> expected = new ArrayList<Balance>();
        Friend friend = new Friend();
        expected.add(new Balance(friend, 50.00));
        Mockito.when(repository.getFinalBalance()).thenReturn(expected);

        //WHEN
        List<Balance> given = sut.getFinalBalance();

        //THEN
        Assertions.assertEquals(expected.size(), given.size());

    }

}
