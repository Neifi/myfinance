package es.neifi.myfinance.registry.application.searchRegistry;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RegistryListResponse {
    private List<RegistryResponse> registryResponses;
    private double totalCost;
    private Long[] timePeriod;
}
