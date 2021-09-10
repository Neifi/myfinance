package es.neifi.myfinance.accountBalance.application;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.accountBalance.domain.Amount;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.domain.UserID;

public class AccountBalanceUpdater {

    private final AccountBalanceRepository accountBalanceRepository;
    private final UserService userService;

    public AccountBalanceUpdater(AccountBalanceRepository accountBalanceRepository, UserService userService) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.userService = userService;
    }

    public void updateAccountBalance(UserID userID, Amount amountToAdd){
        userService.find(userID.value());
        AccountBalance accountBalance = accountBalanceRepository.searchAccountBalance(userID.value())
                .orElseThrow(AccountBalanceNotFoundException::new);
        accountBalanceRepository.updateAccountBalance(userID.value(),accountBalance.totalBalance().value() + amountToAdd.value());
    }

}
