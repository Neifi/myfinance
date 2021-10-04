package es.neifi.myfinance.accountBalance.application;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.accountBalance.domain.Amount;
import es.neifi.myfinance.registry.domain.RegistryCreatedDomainEvent;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.shared.domain.bus.event.AggregateID;
import es.neifi.myfinance.users.application.exceptions.UserNotFoundException;
import es.neifi.myfinance.users.domain.Avatar;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static es.neifi.myfinance.shared.utils.DateUtils.timestampOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

class AccountBalanceUpdaterShould {

    private final AccountBalanceRepository accountBalanceRepository = Mockito.mock(AccountBalanceRepository.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final AccountBalanceUpdater accountBalanceUpdater = new AccountBalanceUpdater(accountBalanceRepository, userService);
    private final boolean EXPENSE = true;
    private final boolean INCOME = false;

    @Test
    void add_amount_to_user_account_balance() {
        String userId = UUID.randomUUID().toString();
        double actualAmount = 1800.00;
        double amountToAdd = 1000D;
        double expectedAmount = actualAmount + amountToAdd;
        RegistryCreatedDomainEvent incomeRegistryCreatedDomainEvent = createRegistryCreatedDomainEvent(userId, amountToAdd, INCOME);

        AccountBalance expectedAccountBalance = new AccountBalance(
                new UserID(userId),
                new Amount(actualAmount),
                new Currency("EUR"),
                new Date(timestampOf(2021, 12, 1, 1, 1, 0)));
        Mockito.when(accountBalanceRepository.searchAccountBalance(userId)).thenReturn(Optional.of(expectedAccountBalance));

        accountBalanceUpdater.on(incomeRegistryCreatedDomainEvent);

        verify(accountBalanceRepository, Mockito.times(1)).searchAccountBalance(userId);
        verify(accountBalanceRepository, Mockito.times(1)).updateAccountBalance(userId, expectedAmount);

    }

    @Test
    void subtract_amount_to_user_account_balance_when_registry_created_event_is_received() {
        String userId = UUID.randomUUID().toString();
        double actualAmount = 1800.00;
        double amountToSubtract = 1000D;
        double expectedAmount = actualAmount - amountToSubtract;
        RegistryCreatedDomainEvent expenseRegistryCreatedDomainEvent = createRegistryCreatedDomainEvent(userId, amountToSubtract, EXPENSE);

        AccountBalance expectedAccountBalance = new AccountBalance(
                new UserID(userId),
                new Amount(actualAmount),
                new Currency("EUR"),
                new Date(timestampOf(2021, 12, 1, 1, 1, 0)));
        Mockito.when(accountBalanceRepository.searchAccountBalance(userId)).thenReturn(Optional.of(expectedAccountBalance));

        accountBalanceUpdater.on(expenseRegistryCreatedDomainEvent);

        verify(accountBalanceRepository, Mockito.times(1)).searchAccountBalance(userId);
        verify(accountBalanceRepository, Mockito.times(1)).updateAccountBalance(userId, expectedAmount);

    }

    private RegistryCreatedDomainEvent createRegistryCreatedDomainEvent(String userId, double amount, boolean isExpense) {
        AggregateID id = new AggregateID("8c5f74c4-41b8-47b5-82ff-ec5f784add04");

        String category = "some-cat";
        String name = "some-name";
        String currency = "EUR";
        long date = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime();
        return new RegistryCreatedDomainEvent(
                userId, id, category, name, amount, currency, date, isExpense
        );
    }

    @Test
    void throw_exception_when_user_dont_exist() {
        String userId = UUID.randomUUID().toString();

        Mockito.when(userService.find(userId)).thenThrow(new UserNotFoundException(userId));
        Exception userNotFoundException = assertThrows(UserNotFoundException.class, () -> accountBalanceUpdater.addAmountToAccountBalance(new UserID(userId), new Amount(10D)));

        Assertions.assertThat(userNotFoundException.getMessage()).isEqualTo("User not found with ID: " + userId);
    }

    @Test
    void throw_exception_when_account_balance_dont_exist() {
        String userId = UUID.randomUUID().toString();

        Mockito.when(userService.find(userId)).thenReturn(Optional.of(User.createUser(
                new UserID(userId),
                new UserName("test"),
                new Email("email@test.com"),
                new Avatar("https://google.com")
        )));

        Mockito.when(accountBalanceRepository.searchAccountBalance(userId)).thenReturn(Optional.empty());
        Exception userNotFoundException = assertThrows(AccountBalanceNotFoundException.class, () -> accountBalanceUpdater.addAmountToAccountBalance(new UserID(userId), new Amount(10D)));

        Assertions.assertThat(userNotFoundException.getMessage()).isEqualTo("Account balance not found");
    }
}