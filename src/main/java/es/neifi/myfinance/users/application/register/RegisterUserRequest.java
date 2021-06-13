package es.neifi.myfinance.users.application.register;

public class RegisterUserRequest {
    private String id;
    private String name;
    private String email;

    public RegisterUserRequest(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String id() {
        return this.id;
    }
}