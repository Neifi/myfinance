package es.neifi.myfinance.accountBalance.domain;

import java.util.Optional;

public interface AccountBalanceRepository {

    Optional<AccountBalance> searchAccountBalance(String userId);

    void updateAccountBalance(String userId);

}
