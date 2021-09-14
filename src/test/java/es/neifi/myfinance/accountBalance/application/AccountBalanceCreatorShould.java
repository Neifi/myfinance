package es.neifi.myfinance.accountBalance.application;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.accountBalance.domain.Amount;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.application.exceptions.UserNotFoundException;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserRegisteredDomainEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static es.neifi.myfinance.shared.utils.DateUtils.timestampOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountBalanceCreatorShould {

    UserService userService = Mockito.mock(UserService.class);
    private AccountBalanceRepository accountBalanceRepository = Mockito.mock(AccountBalanceRepository.class);
    private AccountBalanceCreator accountBalanceCreator = new AccountBalanceCreator(accountBalanceRepository, userService);

    @Test
    void create_account_balance_when_user_registered_event_is_received() {
        String userId = UUID.randomUUID().toString();

        AccountBalance expectedAccountBalance = new AccountBalance(
                new UserID(userId),
                new Amount(0.0),
                new Currency("EUR"),
                new Date(timestampOf(2021, 12, 1, 1, 1, 0)));
        UserRegisteredDomainEvent userRegisteredDomainEvent = new UserRegisteredDomainEvent(new UserID(userId));
        accountBalanceCreator.on(userRegisteredDomainEvent);
        Mockito.verify(accountBalanceRepository, Mockito.times(1)).saveAccountBalance(expectedAccountBalance);
    }

    @Test
    void throw_exception_when_user_dont_exist() {
        String userId = UUID.randomUUID().toString();
        UserRegisteredDomainEvent userRegisteredDomainEvent = new UserRegisteredDomainEvent(new UserID(userId));
        Mockito.when(userService.find(userId)).thenThrow(new UserNotFoundException(userId));
        Exception exception = assertThrows(UserNotFoundException.class, () -> accountBalanceCreator.on(userRegisteredDomainEvent));
    }
}