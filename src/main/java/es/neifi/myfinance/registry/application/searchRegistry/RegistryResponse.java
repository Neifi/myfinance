package es.neifi.myfinance.registry.application.searchRegistry;

import lombok.Builder;

@Builder
public class RegistryResponse {
    
    private String userId;
    
    private String id;
    
    private String category;
    
    private String name;
    
    private Double cost;
    
    private String currency;
    
    private Long date;
    
    private boolean isExpense;

    public RegistryResponse(
             String userId,
             String id,
             String category,
             String name,
             Double cost,
             String currency,
             Long date,
             boolean isExpense
    ) {

        this.userId = userId;
        this.id = id;
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.currency = currency;
        this.date = date;
        this.isExpense = isExpense;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Double getCost() {
        return cost;
    }

    public String getCurrency() {
        return currency;
    }

    public Long getDate() {
        return date;
    }

    public boolean isExpense() {
        return isExpense;
    }
}
