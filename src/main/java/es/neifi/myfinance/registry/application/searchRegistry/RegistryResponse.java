package es.neifi.myfinance.registry.application.searchRegistry;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class RegistryResponse {

    @NonNull private String userId;
    @NonNull private String id;
    private String category;
    private String name;
    private double cost;
    private String currency;
    private String date;

}
