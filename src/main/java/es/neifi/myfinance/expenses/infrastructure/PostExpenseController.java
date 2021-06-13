package es.neifi.myfinance.expenses.infrastructure;

import es.neifi.myfinance.expenses.application.saveExpense.ExpenseSaver;
import es.neifi.myfinance.expenses.application.saveExpense.SaveExpenseRequest;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@RestController
public class PostExpenseController {

    private ExpenseSaver expenseSaver;
    private UserService userService;

    public PostExpenseController(ExpenseSaver expenseSaver, UserService userService) {
        this.expenseSaver = expenseSaver;
        this.userService = userService;
    }

    @PostMapping("/users/{userID}/registry/expenses/{id}")
    public ResponseEntity<Void> saveExpense(@PathVariable String userID, @RequestBody Request request, @PathVariable String id) throws ParseException {
        Optional<User> user = userService.search(id);
        if(user.isPresent()) {
            expenseSaver.save(new SaveExpenseRequest(id, request.category, request.name, request.cost, request.currency, request.date));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private static final class Request{
        private String category;
        private String name;
        private double cost;
        private String currency;
        private String date;

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
            return cost;
        }

        public String
        Currency() {
            return currency;
        }

        public String
        Date() {
            return date;
        }
    }
}
