package es.neifi.myfinance.registry.infrastructure;


import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.registry.application.saveRegistry.SaveRegistryCommand;
import es.neifi.myfinance.shared.Infrastructure.apiException.ApiUserNotFoundException;
import es.neifi.myfinance.users.application.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostIncomeController {

    private final RegistrySaver incomeSaver;

    public PostIncomeController(RegistrySaver incomeSaver) {
        this.incomeSaver = incomeSaver;
    }

    @PostMapping("/user/{userId}/income/{id}")
    public ResponseEntity<HttpStatus> saveIncome(@PathVariable String userId, @RequestBody Request registryRequest, @PathVariable String id) {
        SaveRegistryCommand saveIncomeRequest = new SaveRegistryCommand(
                userId,
                id,
                registryRequest.getCategory(),
                registryRequest.getName(),
                registryRequest.getCost(),
                registryRequest.getCurrency(),
                registryRequest.getDate());

        try {
            incomeSaver.saveIncome(saveIncomeRequest);
        } catch (UserNotFoundException e) {
            throw new ApiUserNotFoundException(
                    HttpStatus.NOT_FOUND, e.getMessage(),e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
