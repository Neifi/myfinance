package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.application.searchRegistry.RegistryCostCalculator;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryListResponse;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryResponse;
import es.neifi.myfinance.registry.application.searchRegistry.RegistrySearcher;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper;
import es.neifi.myfinance.shared.domain.UserService;
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
public class GetIncomeController {
    private RegistrySearcher registrySearcher;
    private UserService userService;

    @Autowired
    public GetIncomeController(RegistrySearcher registrySearcher, UserService userService) {
        this.registrySearcher = registrySearcher;
        this.userService = userService;
    }

    @GetMapping("user/{userID}/income/{id}")
    public ResponseEntity<RegistryResponse> getincome(@PathVariable String userID, @PathVariable String id) {

        if (isUserPresent(userID)) {
            Optional<Registry> optionalRegistry = registrySearcher.searchRegistry(id);

            if (optionalRegistry.isPresent()) {
                Registry registry = optionalRegistry.get();
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

    @GetMapping("user/{userID}/income/")
    public ResponseEntity<RegistryListResponse> getIncomes(
            @PathVariable String userID,
            @Nullable @RequestParam Long initialDate,
            @Nullable @RequestParam Long endDate) {

        if (isUserPresent(userID)) {
            List<RegistryResponse> registryData = new ArrayList<>();
            List<Registry> incomes;

            if (initialDate == null || endDate == null) {
                incomes = ResponseMapper.mapToRegistryResponse(
                        registryData,
                        registrySearcher.searchIncomes(userID));
            } else {
                incomes = ResponseMapper.mapToRegistryResponse(
                        registryData,
                        registrySearcher.searchIncomes(userID, initialDate, endDate));
            }

            RegistryListResponse response = mapToIncomeListResponse(initialDate, endDate, registryData, incomes);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    private RegistryListResponse mapToIncomeListResponse(Long initialDate, Long endDate, List<RegistryResponse> registryData, List<Registry> incomes) {
        return RegistryListResponse.builder()
                .registryResponses(registryData)
                .totalCost(RegistryCostCalculator.calculate(incomes))
                .timePeriod(new Long[]{initialDate, endDate})
                .build();
    }

    private boolean isUserPresent(String userID) {
        return userService.search(userID).isPresent();
    }
}