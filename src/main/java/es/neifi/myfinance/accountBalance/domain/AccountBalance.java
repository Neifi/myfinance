package es.neifi.myfinance.accountBalance.domain;

import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.users.domain.UserID;

import java.util.Date;

public class AccountBalance {

    private final UserID userID;
    private final Amount amount;
    private final Currency currency;
    private final Date date;

    public AccountBalance(UserID userID, Amount amount, Currency currency, Date date) {

        this.userID = userID;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }

    public UserID userID() {
        return userID;
    }

    public Amount totalBalance() {
        return amount;
    }

    public Currency currency() {
        return currency;
    }

    public Date date() {
        return date;
    }
}
