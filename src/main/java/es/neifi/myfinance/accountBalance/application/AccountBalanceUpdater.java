package es.neifi.myfinance.accountBalance.application;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.accountBalance.domain.Amount;
import es.neifi.myfinance.registry.domain.RegistryCreatedDomainEvent;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.domain.UserID;
import org.springframework.context.event.EventListener;

public class AccountBalanceUpdater {

    private final AccountBalanceRepository accountBalanceRepository;
    private final UserService userService;

    public AccountBalanceUpdater(AccountBalanceRepository accountBalanceRepository, UserService userService) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.userService = userService;
    }

    @EventListener
    public void on(RegistryCreatedDomainEvent event) {
        if (event.isExpense()) {
            subtractAmountToAccountBalance(new UserID(event.userId()), new Amount(event.cost()));
        } else {
            addAmountToAccountBalance(new UserID(event.userId()), new Amount(event.cost()));

        }
    }

    public void addAmountToAccountBalance(UserID userID, Amount amount) {
        AccountBalance accountBalance = getCurrentAccountBalance(userID);
        accountBalanceRepository.updateAccountBalance(userID.value(), accountBalance.totalBalance().value() + amount.value());
    }

    public void subtractAmountToAccountBalance(UserID userID, Amount amount) {
        AccountBalance accountBalance = getCurrentAccountBalance(userID);
        accountBalanceRepository.updateAccountBalance(userID.value(), accountBalance.totalBalance().value() - amount.value());
    }

    private AccountBalance getCurrentAccountBalance(UserID userID) {
        userService.find(userID.value());
        AccountBalance accountBalance = accountBalanceRepository.searchAccountBalance(userID.value())
                .orElseThrow(AccountBalanceNotFoundException::new);
        return accountBalance;
    }

}
