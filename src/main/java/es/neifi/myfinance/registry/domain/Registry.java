package es.neifi.myfinance.registry.domain;

import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.shared.domain.AggregateRoot;
import es.neifi.myfinance.users.domain.UserID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Builder
public class Registry extends AggregateRoot {
    private UserID userID;
    private RegistryID id;
    private Category category;
    private Name name;
    private Cost cost;
    private Currency currency;
    private Date date;
    private boolean isExpense;

    public Registry(UserID userID, RegistryID id, Category category, Name name, Cost cost, Currency currency, Date date, boolean isExpense) {
        this.userID = userID;
        this.id = id;
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.currency = currency;
        this.date = date;
        this.isExpense = isExpense;
    }

    public static Registry createExpense(UserID userID, RegistryID id, Category category, Name name, Cost cost, Currency currency, Date date) {
        Registry registry = new Registry(userID, id, category, name, cost, currency, date, true);

        registry.record(new RegistryCreatedDomainEvent(userID.value(), id.value(), category.value(), name.value(), cost.value(), currency.getValue(), date.value(), true));

        return registry;

    }

    public static Registry createIncome(UserID userID, RegistryID id, Category category, Name name, Cost cost, Currency currency, Date date) {
        Registry registry = new Registry(userID, id, category, name, cost, currency, date, false);

        RegistryCreatedDomainEvent event = new RegistryCreatedDomainEvent(
                userID.value(),
                id.value(),
                category.value(),
                name.value(),
                cost.value(),
                currency.getValue(),
                date.value(),
                false);

        registry.record(event);

        return registry;

    }

    public double cost() {
        return this.cost.value();
    }
}
