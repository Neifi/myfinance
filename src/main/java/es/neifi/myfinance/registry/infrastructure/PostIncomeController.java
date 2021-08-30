package es.neifi.myfinance.registry.infrastructure;


import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.registry.application.saveRegistry.SaveRegistryCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class PostIncomeController {

    private final RegistrySaver incomeSaver;

    public PostIncomeController(RegistrySaver incomeSaver) {
        this.incomeSaver = incomeSaver;
    }

    @PostMapping("/users/{userId}/registry/incomes/{id}")
    public ResponseEntity<HttpStatus> saveIncome(@PathVariable String userId, @RequestBody RegistryRequest registryRequest, @PathVariable String id) throws ParseException {
        SaveRegistryCommand saveIncomeRequest = new SaveRegistryCommand(
                userId,
                id,
                registryRequest.category(),
                registryRequest.name(),
                registryRequest.retribution(),
                registryRequest.currency(),
                registryRequest.date());

        incomeSaver.saveIncome(saveIncomeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
