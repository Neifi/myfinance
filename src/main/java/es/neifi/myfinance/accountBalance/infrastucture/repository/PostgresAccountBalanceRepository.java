package es.neifi.myfinance.accountBalance.infrastucture.repository;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;

import java.util.Optional;

public class PostgresAccountBalanceRepository implements AccountBalanceRepository {

    @Override
    public Optional<AccountBalance> searchAccountBalance(String userId) {
        return Optional.empty();
    }

    @Override
    public void updateAccountBalance(String userId, Double value) {

    }
}
