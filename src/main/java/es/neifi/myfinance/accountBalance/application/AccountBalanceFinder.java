package es.neifi.myfinance.accountBalance.application;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.shared.domain.UserService;

import java.util.Optional;

public class AccountBalanceFinder {

    private final AccountBalanceRepository accountBalanceRepository;
    private final UserService userService;

    public AccountBalanceFinder(AccountBalanceRepository accountBalanceRepository, UserService userService) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.userService = userService;
    }

    public Optional<AccountBalance> findBalance(String userId) {
        userService.find(userId);
        return accountBalanceRepository.searchAccountBalance(userId);
    }
}
