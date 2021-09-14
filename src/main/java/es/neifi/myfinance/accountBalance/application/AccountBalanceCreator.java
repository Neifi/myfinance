package es.neifi.myfinance.accountBalance.application;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.accountBalance.domain.Amount;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserRegisteredDomainEvent;
import org.springframework.context.event.EventListener;

import java.sql.Timestamp;
import java.time.Instant;

public class AccountBalanceCreator {

    private AccountBalanceRepository accountBalanceRepository;
    private UserService userService;

    public AccountBalanceCreator(AccountBalanceRepository accountBalanceRepository, UserService userService) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.userService = userService;
    }

    @EventListener
    public void on(UserRegisteredDomainEvent event) {
        AccountBalance accountBalance = new AccountBalance(
                new UserID(event.aggregateId().value()),
                new Amount(0.0D),
                new Currency("EUR"),
                new Date(Timestamp.from(Instant.now()).getTime()));
        createAccountBalance(accountBalance);
    }


    public void createAccountBalance(AccountBalance accountBalance) {
        String userId = accountBalance.userID().value();
        userService.find(userId);
        accountBalanceRepository.saveAccountBalance(accountBalance);
    }

    ;
}
