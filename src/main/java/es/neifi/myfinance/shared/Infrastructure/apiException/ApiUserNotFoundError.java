package es.neifi.myfinance.shared.Infrastructure.apiException;

public class ApiUserNotFoundError extends RuntimeException{

    private final StringBuilder message = new StringBuilder();

    public ApiUserNotFoundError(String userId) {
        super();
        this.message.append("User not found with ID: ").append(userId);
    }

    @Override
    public String getMessage() {
        return message.toString();
    }
}
