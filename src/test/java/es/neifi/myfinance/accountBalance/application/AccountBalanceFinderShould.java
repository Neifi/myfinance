package es.neifi.myfinance.accountBalance.application;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.accountBalance.domain.Amount;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.application.exceptions.UserNotFoundException;
import es.neifi.myfinance.users.domain.UserID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static es.neifi.myfinance.shared.utils.DateUtils.timestampOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountBalanceFinderShould {

    private final AccountBalanceRepository accountBalanceRepository = Mockito.mock(AccountBalanceRepository.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final AccountBalanceFinder accountBalanceFinder = new AccountBalanceFinder(accountBalanceRepository, userService);


    @Test
    void find_account_balance_for_existent_user() {
        String userId = UUID.randomUUID().toString();
        AccountBalance expectedAccountBalance = new AccountBalance(
                new UserID(userId),
                new Amount(1800.00),
                new Currency("EUR"),
                new Date(timestampOf(2021, 12, 1, 1, 1, 0)));

        Mockito.when(accountBalanceRepository.searchAccountBalance(userId)).thenReturn(Optional.of(expectedAccountBalance));

        Optional<AccountBalance> actualAccountBalance = accountBalanceFinder.findBalance(userId);

        Assertions.assertThat(Optional.of(expectedAccountBalance)).isEqualTo(actualAccountBalance);

    }

    @Test
    void throw_exception_when_user_dont_exist() {
        String userId = UUID.randomUUID().toString();

        Mockito.when(userService.find(userId)).thenThrow(new UserNotFoundException(userId));
        Mockito.when(accountBalanceRepository.searchAccountBalance(userId)).thenReturn(Optional.empty());
        Exception userNotFoundException = assertThrows(UserNotFoundException.class, () -> accountBalanceFinder.findBalance(userId));

        Assertions.assertThat(userNotFoundException.getMessage()).isEqualTo("User not found with ID: " + userId);
    }

}