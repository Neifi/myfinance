package es.neifi.myfinance.shared.Infrastructure.apiException;

import java.time.LocalDateTime;

public class ApiUserNotFoundError extends RuntimeException{
    private StringBuilder message = new StringBuilder();
    private String date = LocalDateTime.now().toString();
    public ApiUserNotFoundError(String userId) {
        super();
        this.message.append("User not found with ID: ").append(userId);
    }

    @Override
    public String getMessage() {
        return message.toString();
    }
}
