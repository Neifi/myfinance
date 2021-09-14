package es.neifi.myfinance.accountBalance.domain;

import java.util.Optional;

public interface AccountBalanceRepository {

    void saveAccountBalance(AccountBalance accountBalance);

    Optional<AccountBalance> searchAccountBalance(String userId);

    void updateAccountBalance(String userId,Double value);

}
