package es.neifi.myfinance.users.domain;

public class EmailValidator {

    public boolean isValid(String email){
        return email.contains("@");
    }
}
