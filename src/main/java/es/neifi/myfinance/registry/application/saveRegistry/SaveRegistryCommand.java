package es.neifi.myfinance.registry.application.saveRegistry;

import lombok.Builder;

@Builder
public class SaveRegistryCommand {
    private String userId;
    private String registryId;
    private String category;
    private String name;
    private double cost;
    private String currency;
    private Long date;

    public SaveRegistryCommand(String userId, String registryId, String category, String name, Double cost, String currency, Long date) {
        this.userId = userId;
        this.registryId = registryId;
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.currency = currency;
        this.date = date;
    }

    public String
    id() {
        return registryId;
    }

    public String
    category() {
        return category;
    }

    public String
    name() {
        return name;
    }

    public double
    cost() {
        return cost;
    }

    public String
    currency() {
        return currency;
    }

    public Long
    date() {
        return date;
    }

    public String userId(){
        return this.userId;
    }
}
