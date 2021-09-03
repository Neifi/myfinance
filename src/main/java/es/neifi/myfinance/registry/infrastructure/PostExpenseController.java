package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.registry.application.saveRegistry.SaveRegistryCommand;
import es.neifi.myfinance.shared.Infrastructure.apiException.ApiUserNotFoundError;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("")
public class PostExpenseController {

    private final RegistrySaver registrySaver;
    private final UserService userService;

    public PostExpenseController(RegistrySaver registrySaver, UserService userService) {
        this.registrySaver = registrySaver;
        this.userService = userService;
    }

    @PostMapping("/user/{userId}/expense/{registryId}")
    public ResponseEntity<Void> saveExpense(@PathVariable String userId, @RequestBody Request request, @PathVariable String registryId) {
        Optional<User> user = userService.find(userId);
        if (user.isPresent()) {
            registrySaver.saveExpense(
                    new SaveRegistryCommand(
                            userId,
                            registryId,
                            request.getCategory(),
                            request.getName(),
                            request.getCost(),
                            request.getCurrency(),
                            request.getDate()));

            return ResponseEntity.status(HttpStatus.CREATED).build();

        }

        throw new ApiUserNotFoundError(userId);
    }
}
