package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.application.searchRegistry.ExpenseSearcher;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryListResponse;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryResponse;
import es.neifi.myfinance.registry.domain.vo.*;
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
            Optional<Registry> optionalExpense = expenseSearcher.search(id);

            if (optionalExpense.isPresent()) {
                Registry registry = optionalExpense.get();
                RegistryResponse registryResponse = RegistryResponse.builder()
                        .id(registry.getId().value())
                        .category(registry.getCategory().value())
                        .name(registry.getName().value())
                        .cost(registry.cost())
                        .currency(registry.getCurrency().getValue())
                        .date(registry.getDate().getValue())
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
    public ResponseEntity<RegistryListResponse> getExpenses(@PathVariable String userID, @Nullable @RequestParam String initialDate, @Nullable @RequestParam String endDate) {
        if (isUserPresent(userID)) {
            List<RegistryResponse> registryResponse = new ArrayList<>();
            List<Registry> expens;

            if (initialDate == null || endDate == null) {
                expens = ResponseMapper.mapToExpenseResponse(initialDate, endDate, registryResponse,expenseSearcher.search());
            } else {
                expens = ResponseMapper.mapToExpenseResponse(initialDate, endDate, registryResponse,expenseSearcher.search(initialDate, endDate));
            }

            RegistryListResponse response = mapToExpenseListResponse(initialDate, endDate, registryResponse, expens);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    private RegistryListResponse mapToExpenseListResponse(String initialDate, String endDate, List<RegistryResponse> registryResponse, List<Registry> expens) {
        return RegistryListResponse.builder()
                .expenses(registryResponse)
                .totalExpended(MoneyCalculator.calculate(expens))
                .timePeriod(new String[]{initialDate, endDate})
                .build();
    }

}
