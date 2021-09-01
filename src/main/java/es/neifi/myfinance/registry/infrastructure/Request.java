package es.neifi.myfinance.registry.infrastructure;


final class Request {
    private String category;
    private String name;
    private Double cost;
    private String currency;
    private Long date;

    public Request(String category, String name, Double cost, String currency, Long date) {
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.currency = currency;
        this.date = date;
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

    @Override
    public String toString() {
        return "{"
                + "\"category\":\"" + category + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"cost\":\"" + cost + "\""
                + ", \"currency\":\"" + currency + "\""
                + ", \"date\":\"" + date + "\""
                + "}";
    }
}
