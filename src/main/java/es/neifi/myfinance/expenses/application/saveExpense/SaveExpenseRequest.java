package es.neifi.myfinance.expenses.application.saveExpense;

import lombok.Getter;

public class SaveExpenseRequest {
    private String id;
    private String category;
    private String name;
    private double cost;
    private String currency;
    private String date;

    public SaveExpenseRequest(String id, String category, String name, double cost, String currency, String date) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.currency = currency;
        this.date = date;
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
}
