package es.neifi.myfinance.accountBalance.application;

import es.neifi.myfinance.shared.application.exception.ApplicationException;

public class AccountBalanceNotFoundException extends ApplicationException {
    public AccountBalanceNotFoundException() {
        super("Account balance not found");
    }
}
