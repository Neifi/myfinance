package es.neifi.myfinance.accountBalance.application;

public class AccountBalanceNotFoundException extends RuntimeException{
    public AccountBalanceNotFoundException() {
        super("Account balance not found");
    }
}
