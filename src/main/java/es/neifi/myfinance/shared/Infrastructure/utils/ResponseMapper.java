package es.neifi.myfinance.shared.Infrastructure.utils;

import es.neifi.myfinance.registry.application.searchRegistry.RegistryResponse;
import es.neifi.myfinance.registry.domain.Registry;

import java.util.List;

public class ResponseMapper {

    public static List<Registry> mapToExpenseResponse(String initialDate, String endDate, List<RegistryResponse> registryResponse, List<Registry> expens) {

        for (Registry registry : expens) {
            registryResponse.add(RegistryResponse.builder()
                    .id(registry.getId().value())
                    .category(registry.getCategory().value())
                    .name(registry.getName().value())
                    .cost(registry.cost())
                    .currency(registry.getCurrency().getValue())
                    .date(registry.getDate().getValue())
                    .build());
        }
        return expens;
    }
}
