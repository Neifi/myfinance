package es.neifi.myfinance.registry.domain.vo;

import es.neifi.myfinance.registry.domain.RegistryCreatedDomainEvent;
import es.neifi.myfinance.shared.domain.AggregateRoot;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Builder
public class Registry extends AggregateRoot {
    private RegistryID id;
    private Category category;
    private Name name;
    private Cost cost;
    private Currency currency;
    private Date date;
    private boolean isExpense;

    public Registry(RegistryID id, Category category, Name name, Cost cost, Currency currency, Date date, boolean isExpense) {

        this.id = id;
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.currency = currency;
        this.date = date;
        this.isExpense = isExpense;
    }

    public static Registry create(RegistryID id, Category category, Name name, Cost cost, Currency currency, Date date, boolean isExpense){
        Registry registry = new Registry(id, category, name, cost, currency, date, isExpense);

        registry.record(new RegistryCreatedDomainEvent(id.value(), category.value(), name.value(), cost.value(), currency.getValue(), date.value(), isExpense));

        return registry;

    }

    public double cost() {
        return this.cost.value();
    }
}
