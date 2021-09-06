package es.neifi.myfinance.registry.domain;

import es.neifi.myfinance.shared.domain.bus.event.DomainEvent;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;

@EqualsAndHashCode
public class RegistryCreatedDomainEvent extends DomainEvent<RegistryCreatedDomainEvent> {

    private String userId;
    private String name;
    private double cost;
    private String category;
    private String currency;
    private Long date;
    private boolean isExpense;

    public RegistryCreatedDomainEvent(String userId, String id, String category, String name, double cost, String currency, Long date, boolean isExpense) {
        super(id);
        this.userId = userId;
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.currency = currency;
        this.date = date;
        this.isExpense = isExpense;
    }

    public RegistryCreatedDomainEvent(String userId, String aggregateId, String eventId, Long occurredOn, String category, String name, double cost, String currency, Long date, boolean isExpense) {
        super(aggregateId, eventId, occurredOn);
        this.userId = userId;
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.currency = currency;
        this.date = date;
        this.isExpense = isExpense;
    }

    @Override
    public String eventName() {
        return "registry.created";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {{
            put("userId", userId);
            put("registryId", RegistryCreatedDomainEvent.super.getAggregateId());
            put("name", name);
            put("category", category);
            put("cost", cost);
            put("currency", currency);
            put("date", date);
            put("isExpense", isExpense);
        }};
    }

    @Override
    protected RegistryCreatedDomainEvent fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, Long occurredOn) {
        return new RegistryCreatedDomainEvent(
                (String) body.get("userId"),
                aggregateId,
                eventId,
                occurredOn,
                (String) body.get("category"),
                (String) body.get("name"),
                (double) body.get("cost"),
                (String) body.get("currency"),
                (Long) body.get("date"),
                (boolean) body.get("isExpense")

        );
    }

    public String userId() {
        return userId;
    }

    public String name() {
        return name;
    }

    public double cost() {
        return cost;
    }

    public String category() {
        return category;
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
