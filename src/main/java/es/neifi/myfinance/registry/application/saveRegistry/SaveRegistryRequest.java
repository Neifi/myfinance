package es.neifi.myfinance.registry.application.saveRegistry;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class SaveRegistryRequest {
    private String id;
    private String category;
    private String name;
    private double cost;
    private String currency;
    private String date;
    private boolean isExpense;

    public SaveRegistryRequest(String id, String category, String name, double cost, String currency, String date, boolean isExpense) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.currency = currency;
        this.date = date;
        this.isExpense = isExpense;
    }

    public String
    id() {
        return id;
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

    public String
    date() {
        return date;
    }

    public boolean isExpense(){
        return isExpense;
    }
}
