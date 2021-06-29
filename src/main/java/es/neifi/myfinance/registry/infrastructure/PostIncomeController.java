package es.neifi.myfinance.registry.infrastructure;


import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.registry.application.saveRegistry.SaveRegistryRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class PostIncomeController {

    private RegistrySaver incomeSaver;

    public PostIncomeController(RegistrySaver incomeSaver) {
        this.incomeSaver = incomeSaver;
    }

    @PostMapping("/users/{userId}/registry/incomes/{id}")
    public ResponseEntity saveIncome(@PathVariable String userId, @RequestBody Request request, @PathVariable String id) throws ParseException {
        SaveRegistryRequest saveIncomeRequest = new SaveRegistryRequest(
                id, request.category, request.name,
                request.retribution, request.currency, request.date,request.isExpense);

        incomeSaver.saveIncome(saveIncomeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private static final class Request {
        private String category;
        private String name;
        private double retribution;
        private String currency;
        private String date;
        private final boolean isExpense = false;

        public String
        Category() {
            return category;
        }

        public String
        Name() {
            return name;
        }

        public double
        Cost() {
            return retribution;
        }

        public String
        Currency() {
            return currency;
        }

        public String
        Date() {
            return date;
        }

        public boolean isExpense(){
            return isExpense;
        }
    }
}
