package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.application.searchRegistry.RegistryCostCalculator;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryListResponse;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryResponse;
import es.neifi.myfinance.registry.application.searchRegistry.RegistrySearcher;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper;
import es.neifi.myfinance.users.application.UserNotFoundException;
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

    @Autowired
    public GetIncomeController(RegistrySearcher registrySearcher) {
        this.registrySearcher = registrySearcher;
    }

    @GetMapping("user/{userID}/income/{id}")
    public ResponseEntity<RegistryResponse> getincome(@PathVariable String userID, @PathVariable String id) {


            Optional<Registry> optionalRegistry = registrySearcher.findRegistry(id);

            if (optionalRegistry.isPresent()) {
                Registry registry = optionalRegistry.get();
                RegistryResponse registryResponse = RegistryResponse.builder()
                        .userId(registry.userId().value())
                        .id(registry.id().value())
                        .category(registry.category().value())
                        .name(registry.name().value())
                        .cost(registry.cost().value())
                        .currency(registry.currency().value())
                        .date(registry.date().value())
                        .isExpense(registry.isExpense())
                        .build();
                return ResponseEntity.ok(registryResponse);
            }


        return ResponseEntity.notFound().build();
    }

    @GetMapping("user/{userID}/income/")
    public ResponseEntity<RegistryListResponse> getIncomes(
            @PathVariable String userID,
            @Nullable @RequestParam Long initialDate,
            @Nullable @RequestParam Long endDate) {

        try {
            List<RegistryResponse> registryData = new ArrayList<>();
            List<Registry> incomes;

            if (initialDate == null || endDate == null) {
                incomes = ResponseMapper.mapToRegistryResponse(
                        registryData,
                        registrySearcher.findIncomes(userID));
            } else {
                incomes = ResponseMapper.mapToRegistryResponse(
                        registryData,
                        registrySearcher.findIncomes(userID, initialDate, endDate));
            }

            RegistryListResponse response = mapToIncomeListResponse(initialDate, endDate, registryData, incomes);

            return ResponseEntity.ok(response);
        }catch (UserNotFoundException exception){

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

}