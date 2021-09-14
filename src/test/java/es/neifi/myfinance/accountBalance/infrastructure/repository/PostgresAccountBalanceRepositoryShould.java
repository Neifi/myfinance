package es.neifi.myfinance.accountBalance.infrastructure.repository;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.accountBalance.domain.Amount;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.users.domain.UserID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static es.neifi.myfinance.shared.utils.DateUtils.timestampOf;

@SpringBootTest
class PostgresAccountBalanceRepositoryShould {

    @Autowired
    private AccountBalanceRepository accountBalanceRepository;



    @Test
    void search_account_balance_in_db() {
        String userId = UUID.randomUUID().toString();
        AccountBalance expectedAccountBalance = new AccountBalance(
                new UserID(userId),
                new Amount(1000D),
                new Currency("EUR"),
                new Date(timestampOf(2021, 12, 1, 1, 1, 0)));

        accountBalanceRepository.saveAccountBalance(expectedAccountBalance);
        Optional<AccountBalance> accountBalance = accountBalanceRepository.searchAccountBalance(userId);

        Assertions.assertThat(accountBalance).isEqualTo(Optional.of(expectedAccountBalance));
    }

    @Test
    void save_account_balance_in_db() {
        String userId = UUID.randomUUID().toString();
        AccountBalance expectedAccountBalance = new AccountBalance(
                new UserID(userId),
                new Amount(1000D),
                new Currency("EUR"),
                new Date(timestampOf(2021, 12, 1, 1, 1, 0)));

        accountBalanceRepository.saveAccountBalance(expectedAccountBalance);
    }

    @Test
    void update_account_balance_in_db() {
        String userId = UUID.randomUUID().toString();
        AccountBalance expectedAccountBalance = new AccountBalance(
                new UserID(userId),
                new Amount(1000D),
                new Currency("EUR"),
                new Date(timestampOf(2021, 12, 1, 1, 1, 0)));

        accountBalanceRepository.saveAccountBalance(expectedAccountBalance);
        accountBalanceRepository.updateAccountBalance(userId,1200D);

        Optional<AccountBalance> accountBalance = accountBalanceRepository.searchAccountBalance(userId);

        Assertions.assertThat(accountBalance).isEqualTo(Optional.of(expectedAccountBalance));
    }






}