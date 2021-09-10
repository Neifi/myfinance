package es.neifi.myfinance.accountBalance.domain;

import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.users.domain.UserID;

import java.util.Date;

public class AccountBalance {

    private final UserID userID;
    private final TotalBalance totalBalance;
    private final Currency currency;
    private final Date date;

    public AccountBalance(UserID userID, TotalBalance totalBalance, Currency currency, Date date) {

        this.userID = userID;
        this.totalBalance = totalBalance;
        this.currency = currency;
        this.date = date;
    }

    public UserID userID() {
        return userID;
    }

    public TotalBalance totalBalance() {
        return totalBalance;
    }

    public Currency currency() {
        return currency;
    }

    public Date date() {
        return date;
    }
}
