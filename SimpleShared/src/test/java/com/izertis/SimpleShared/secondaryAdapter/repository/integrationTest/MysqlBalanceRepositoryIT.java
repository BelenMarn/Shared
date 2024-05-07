package com.izertis.SimpleShared.secondaryAdapter.repository.integrationTest;

import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.secondaryAdapter.repository.BalanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MysqlBalanceRepositoryIT {

    private final BalanceRepository balanceRepository;

    @Autowired
    public MysqlBalanceRepositoryIT(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Test
    public void shouldReturnNumberOfFriends() throws EmptyExpenseListException {
        assertThat(balanceRepository.getNumberOfFriends()).isGreaterThan(0);
    }

    @Test
    public void shouldReturnAverageSpending() throws EmptyExpenseListException {
        double averageSpending = balanceRepository.getAverageSpending();
        assertThat(averageSpending).isGreaterThan(0);
    }

    @Test
    public void shouldReturnBalanceOfFriend(){
        double balanceFriend = balanceRepository.getBalanceOfFriend(1);
        assertThat(balanceFriend).isGreaterThan(0);
    }
}
