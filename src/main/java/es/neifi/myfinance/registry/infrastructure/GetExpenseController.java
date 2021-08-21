package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.application.searchRegistry.RegistryCostCalculator;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryListResponse;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryResponse;
import es.neifi.myfinance.registry.application.searchRegistry.RegistrySearcher;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper;
import es.neifi.myfinance.shared.domain.UserService;
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

    private final RegistrySearcher registrySearcher;
    private final UserService userService;

    public GetExpenseController(RegistrySearcher registrySearcher, UserService userService) {
        this.registrySearcher = registrySearcher;
        this.userService = userService;
    }

    @GetMapping("user/{userID}/expenses/{id}")
    public ResponseEntity<RegistryResponse> getExpense(@PathVariable String userID, @PathVariable String id) {

        if (isUserPresent(userID)) {
            Optional<Registry> optionalExpense = registrySearcher.searchRegistry(id);

            if (optionalExpense.isPresent()) {
                Registry registry = optionalExpense.get();
                RegistryResponse registryResponse = RegistryResponse.builder()
                        .userId(registry.getUserId().value())
                        .id(registry.getId().value())
                        .category(registry.getCategory().value())
                        .name(registry.getName().value())
                        .cost(registry.cost())
                        .currency(registry.getCurrency().getValue())
                        .date(registry.getDate().value())
                        .isExpense(registry.isExpense())
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
    public ResponseEntity<RegistryListResponse> getExpenses(
            @PathVariable String userID,
            @Nullable @RequestParam Long initialDate,
            @Nullable @RequestParam Long endDate) {

        if (isUserPresent(userID)) {
            List<RegistryResponse> registryData = new ArrayList<>();
            List<Registry> expenses;

            if (initialDate == null || endDate == null) {
                expenses = ResponseMapper.mapToExpenseResponse(
                        registryData,
                        registrySearcher.searchExpenses(userID));
            } else {
                expenses = ResponseMapper.mapToExpenseResponse(
                        registryData,
                        registrySearcher.searchExpenses(userID, initialDate, endDate));
            }

            RegistryListResponse response = mapToExpenseListResponse(initialDate, endDate, registryData, expenses);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    private RegistryListResponse mapToExpenseListResponse(Long initialDate, Long endDate, List<RegistryResponse> registryData, List<Registry> expens) {
        return RegistryListResponse.builder()
                .expenses(registryData)
                .totalExpended(RegistryCostCalculator.calculate(expens))
                .timePeriod(new Long[]{initialDate, endDate})
                .build();
    }

}
