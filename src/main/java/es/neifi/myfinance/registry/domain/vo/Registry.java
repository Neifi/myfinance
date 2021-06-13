package es.neifi.myfinance.registry.domain.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Builder
public class Registry {
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

    public double cost() {
        return this.cost.value();
    }
}
