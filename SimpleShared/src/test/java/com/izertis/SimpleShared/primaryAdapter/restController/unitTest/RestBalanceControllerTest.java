package com.izertis.SimpleShared.primaryAdapter.restController.unitTest;

import com.izertis.SimpleShared.core.domain.Balance;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.NoBalanceFoundException;
import com.izertis.SimpleShared.core.service.BalanceService;
import com.izertis.SimpleShared.primaryAdapter.respond.BalanceResponse;
import com.izertis.SimpleShared.primaryAdapter.restController.RestBalanceController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class RestBalanceControllerTest {
    private final BalanceService service = mock(BalanceService.class);
    private final RestBalanceController sut = new RestBalanceController(service);

    @Test
    public void shouldGetFinalBalance() throws NoBalanceFoundException, EmptyExpenseListException, EmptyFriendListException {
        //GIVEN
        final List<Balance> training = new ArrayList<Balance>();
        final Friend friend = new Friend(1, "Juan");
        training.add(new Balance(friend, 50.00));

        when(service.getFinalBalance()).thenReturn(training);

        final List<BalanceResponse> expected = new ArrayList<>();
        final BalanceResponse balanceResponse = new BalanceResponse(friend.getName(), 50.00);
        expected.add(balanceResponse);

        //WHEN
        List<BalanceResponse> result = sut.getFinalBalance();

        //THEN
        Assertions.assertEquals(expected.size(), result.size());
        org.assertj.core.api.Assertions.assertThat(result).containsExactlyElementsOf(expected);
    }
}
