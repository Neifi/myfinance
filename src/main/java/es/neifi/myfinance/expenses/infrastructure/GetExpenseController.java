package es.neifi.myfinance.expenses.infrastructure;

import es.neifi.myfinance.expenses.application.searchExpense.ExpenseListResponse;
import es.neifi.myfinance.expenses.application.searchExpense.RegistryResponse;
import es.neifi.myfinance.expenses.application.searchExpense.ExpenseSearcher;
import es.neifi.myfinance.expenses.domain.vo.*;
import es.neifi.myfinance.shared.domain.UserService;

import es.neifi.myfinance.shared.application.calculations.MoneyCalculator;
import es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class GetExpenseController {

    @Autowired
    private ExpenseSearcher expenseSearcher;

    @Autowired
    private UserService userService;

    @GetMapping("user/{userID}/expenses/{id}")
    public ResponseEntity<RegistryResponse> getExpense(@PathVariable String userID, @PathVariable String id) {

        if (isUserPresent(userID)) {
            Optional<Expense> optionalExpense = expenseSearcher.search(id);

            if (optionalExpense.isPresent()) {
                Expense expense = optionalExpense.get();
                RegistryResponse registryResponse = RegistryResponse.builder()
                        .id(expense.getId().value())
                        .category(expense.getCategory().value())
                        .name(expense.getName().value())
                        .money(expense.getExpenseCost().value())
                        .currency(expense.getCurrency().getValue())
                        .date(expense.getDate().getValue())
                        .build();
                return ResponseEntity.ok(registryResponse);
            }

        }


        return ResponseEntity.notFound().build();
    }

    private boolean isUserPresent(String userID) {
        return userService.search(userID).isPresent();
    }

    @GetMapping("user/{userID}/expenses/")
    public ResponseEntity<ExpenseListResponse> getExpenses(@PathVariable String userID, @Nullable @RequestParam String initialDate, @Nullable @RequestParam String endDate) {
        if (isUserPresent(userID)) {
            List<RegistryResponse> registryResponse = new ArrayList<>();
            List<Expense> expenses;

            if (initialDate == null || endDate == null) {
                expenses = ResponseMapper.mapToExpenseResponse(initialDate, endDate, registryResponse,expenseSearcher.search());
            } else {
                expenses = ResponseMapper.mapToExpenseResponse(initialDate, endDate, registryResponse,expenseSearcher.search(initialDate, endDate));
            }

            ExpenseListResponse response = mapToExpenseListResponse(initialDate, endDate, registryResponse, expenses);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    private ExpenseListResponse mapToExpenseListResponse(String initialDate, String endDate, List<RegistryResponse> registryResponse, List<Expense> expenses) {
        return ExpenseListResponse.builder()
                .expenses(registryResponse)
                .totalExpended(MoneyCalculator.calculateExpenses(expenses))
                .timePeriod(new String[]{initialDate, endDate})
                .build();
    }

}
