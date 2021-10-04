package es.neifi.myfinance.users.application.register;

import org.springframework.web.multipart.MultipartFile;

public class RegisterUserCommand {
    private String id;
    private String name;
    private String email;
    private MultipartFile avatar;

    public RegisterUserCommand(String id, String name, String email,MultipartFile avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
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

    public MultipartFile avatar() {
        return avatar;
    }
}
