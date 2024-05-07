package com.izertis.SimpleShared.primaryAdapter.restController;

import com.izertis.SimpleShared.core.domain.Balance;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.NoBalanceFoundException;
import com.izertis.SimpleShared.core.service.BalanceService;
import com.izertis.SimpleShared.primaryAdapter.respond.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/balance")
public class RestBalanceController {
    @Autowired
    private final BalanceService balanceService;

    public RestBalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping
    public List<BalanceResponse> getFinalBalance() throws NoBalanceFoundException, EmptyExpenseListException, EmptyFriendListException {
        List<Balance> balanceToPresent = balanceService.getFinalBalance();
        return toBalanceRespond(balanceToPresent);
    }

    private List<BalanceResponse> toBalanceRespond(List<Balance> balanceToPresent) {
        return balanceToPresent.stream()
                .map(balance -> new BalanceResponse(
                        balance.getFriend().getName(),
                        balance.getBalance()))
                .collect(Collectors.toList());
    }
}
