package es.neifi.myfinance.users.application.register;

import java.io.File;

public class RegisterUserCommand {
    private String id;
    private String name;
    private String email;
    private File avatar;

    public RegisterUserCommand(String id, String name, String email,File avatar) {
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

    public File avatar() {
        return avatar;
    }
}
