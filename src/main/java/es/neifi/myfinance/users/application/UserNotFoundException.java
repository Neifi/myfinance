package es.neifi.myfinance.users.application;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String id) {
        super("user not found with ID:" + id);
    }
}
