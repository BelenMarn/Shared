package com.izertis.SimpleShared.secondaryAdapter.repository;

import com.izertis.SimpleShared.core.domain.Balance;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;

import java.util.List;

public interface BalanceRepository {
    List<Balance> getFinalBalance() throws EmptyExpenseListException, EmptyFriendListException;
    int getNumberOfFriends() throws EmptyExpenseListException;
    double getAverageSpending() throws EmptyExpenseListException;
    double getBalanceOfFriend(long id);
}
