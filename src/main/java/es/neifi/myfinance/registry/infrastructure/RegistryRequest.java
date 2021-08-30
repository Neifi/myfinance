package es.neifi.myfinance.registry.infrastructure;

import lombok.Builder;

@Builder
public final class RegistryRequest {
    private String category;
    private String name;
    private double retribution;
    private String currency;
    private Long date;
    private final boolean isExpense = false;

    public RegistryRequest(String category, String name, double retribution, String currency, Long date) {
        this.category = category;
        this.name = name;
        this.retribution = retribution;
        this.currency = currency;
        this.date = date;
    }

    public String category() {
        return category;
    }

    public String name() {
        return name;
    }

    public double retribution() {
        return retribution;
    }

    public String currency() {
        return currency;
    }

    public Long date() {
        return date;
    }

    public boolean isExpense() {
        return isExpense;
    }
}