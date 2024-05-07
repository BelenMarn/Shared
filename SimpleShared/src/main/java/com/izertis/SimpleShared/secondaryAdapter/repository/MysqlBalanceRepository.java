package com.izertis.SimpleShared.secondaryAdapter.repository;

import com.izertis.SimpleShared.core.domain.Balance;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.secondaryAdapter.entity.BalanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MysqlBalanceRepository implements BalanceRepository {
    private final String GET_NUMBER_OF_FRIENDS = "SELECT COUNT(id_friend) FROM friend";
    private final String GET_SUM_SPENDING = "SELECT SUM(amount) FROM expense";
    private final String GET_EXPENSE_OF_FRIEND = "SELECT SUM(amount) FROM expense WHERE id_friend = ?";

    private JdbcTemplate jdbcTemplate;
    private FriendRepository friendRepository;

    @Autowired
    public MysqlBalanceRepository(JdbcTemplate jdbcTemplate, FriendRepository friendRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendRepository = friendRepository;
    }

    @Override
    public List<Balance> getFinalBalance() throws EmptyExpenseListException, EmptyFriendListException {
        double averageSpending = getAverageSpending();
        List<BalanceEntity> balanceEntity = new ArrayList<>();

        List<Friend> friends = friendRepository.findAll();
        for(Friend friend : friends) {

            BalanceEntity entity = new BalanceEntity();
            entity.setIdFriend(friend.getIdFriend());
            entity.setName(friend.getName());
            entity.setBalance((getBalanceOfFriend(friend.getIdFriend())) - averageSpending);

            balanceEntity.add(entity);
        }

        return toDomainBalance(balanceEntity);
    }

    @Override
    public int getNumberOfFriends() throws EmptyExpenseListException {
        Integer numberOfFriends = jdbcTemplate.queryForObject(GET_NUMBER_OF_FRIENDS, Integer.class);

        if (numberOfFriends == null){
            throw new EmptyExpenseListException("No friends found");
        }else{
            return numberOfFriends;
        }
    }

    @Override
    public double getAverageSpending() throws EmptyExpenseListException {
        int numberOfFriends = getNumberOfFriends();
        double totalSum = jdbcTemplate.queryForObject(GET_SUM_SPENDING, Double.class);

        return totalSum / numberOfFriends;
    }

    @Override
    public double getBalanceOfFriend(long id) {
        Double totalSpending = jdbcTemplate.queryForObject(GET_EXPENSE_OF_FRIEND, Double.class, id);
        if (totalSpending != null) {
            return totalSpending.doubleValue();
        } else {
            return 0.0;
        }
    }

    private List<Balance> toDomainBalance(List<BalanceEntity> balanceEntity) {
        return balanceEntity.stream()
                .map(balanceE -> {
                    Friend friend = new Friend(balanceE.getIdFriend(), balanceE.getName());
                    return new Balance(friend, balanceE.getBalance());
                })
        .collect(Collectors.toList());
    }
}
