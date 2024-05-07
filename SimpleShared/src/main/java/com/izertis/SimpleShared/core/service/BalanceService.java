package com.izertis.SimpleShared.core.service;

import com.izertis.SimpleShared.core.domain.Balance;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.NoBalanceFoundException;
import com.izertis.SimpleShared.secondaryAdapter.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BalanceService {
    private final BalanceRepository balanceRepository;

    @Autowired
    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public List<Balance> getFinalBalance() throws NoBalanceFoundException, EmptyExpenseListException, EmptyFriendListException {
        final List<Balance> balanceToPresent = new ArrayList<Balance>();
        final Iterator<Balance> balanceIterator = balanceRepository.getFinalBalance().iterator();
        while (balanceIterator.hasNext()) {
            balanceToPresent.add(balanceIterator.next());
        }

        if(!balanceToPresent.isEmpty()){
            return balanceToPresent;
        }else{
            throw new NoBalanceFoundException("No balance found");
        }
    }

}
