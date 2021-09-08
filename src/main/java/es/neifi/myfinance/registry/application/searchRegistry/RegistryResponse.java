package es.neifi.myfinance.registry.application.searchRegistry;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class RegistryResponse {

    @NonNull
    private String userId;
    @NonNull
    private String id;
    @NonNull
    private String category;
    @NonNull
    private String name;
    @NonNull
    private Double cost;
    @NonNull
    private String currency;
    @NonNull
    private Long date;
    @NonNull
    private boolean isExpense;

    public RegistryResponse(
            @NonNull String userId,
            @NonNull String id,
            @NonNull String category,
            @NonNull String name,
            @NonNull Double cost,
            @NonNull String currency,
            @NonNull Long date,
            @NonNull boolean isExpense
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
