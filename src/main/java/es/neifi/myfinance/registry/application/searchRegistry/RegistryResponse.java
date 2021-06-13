package es.neifi.myfinance.registry.application.searchRegistry;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistryResponse {

    private String id;
    private String category;
    private String name;
    private double cost;
    private String currency;
    private String date;

}
