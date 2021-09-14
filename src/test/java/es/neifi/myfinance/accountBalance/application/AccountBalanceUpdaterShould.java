package es.neifi.myfinance.accountBalance.application;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.accountBalance.domain.Amount;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.application.exceptions.UserNotFoundException;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static es.neifi.myfinance.shared.utils.DateUtils.timestampOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

class AccountBalanceUpdaterShould {

    private final AccountBalanceRepository accountBalanceRepository = Mockito.mock(AccountBalanceRepository.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final AccountBalanceUpdater accountBalanceUpdater = new AccountBalanceUpdater(accountBalanceRepository, userService);

    @Test
    void update_user_account_balance() {
        String userId = UUID.randomUUID().toString();
        double actualAmount = 1800.00;
        double amountToAdd = 1000D;
        double expectedAmount = actualAmount + amountToAdd;

        AccountBalance expectedAccountBalance = new AccountBalance(
                new UserID(userId),
                new Amount(actualAmount),
                new Currency("EUR"),
                new Date(timestampOf(2021, 12, 1, 1, 1, 0)));
        Mockito.when(accountBalanceRepository.searchAccountBalance(userId)).thenReturn(Optional.of(expectedAccountBalance));

        accountBalanceUpdater.updateAccountBalance(new UserID(userId), new Amount(amountToAdd));

        verify(accountBalanceRepository, Mockito.times(1)).searchAccountBalance(userId);
        verify(accountBalanceRepository, Mockito.times(1)).updateAccountBalance(userId, expectedAmount);

    }

    @Test
    void throw_exception_when_user_dont_exist() {
        String userId = UUID.randomUUID().toString();

        Mockito.when(userService.find(userId)).thenThrow(new UserNotFoundException(userId));
        Exception userNotFoundException = assertThrows(UserNotFoundException.class, () -> accountBalanceUpdater.updateAccountBalance(new UserID(userId),new Amount(10D)));

        Assertions.assertThat(userNotFoundException.getMessage()).isEqualTo("User not found with ID: " + userId);
    }

    @Test
    void throw_exception_when_account_balance_dont_exist() {
        String userId = UUID.randomUUID().toString();

        Mockito.when(userService.find(userId)).thenReturn(Optional.of(User.createUser(new UserID(userId),new UserName("test"),new Email("email@test.com"))));

        Mockito.when(accountBalanceRepository.searchAccountBalance(userId)).thenReturn(Optional.empty());
        Exception userNotFoundException = assertThrows(AccountBalanceNotFoundException.class, () -> accountBalanceUpdater.updateAccountBalance(new UserID(userId),new Amount(10D)));

        Assertions.assertThat(userNotFoundException.getMessage()).isEqualTo("Account balance not found");
    }
}