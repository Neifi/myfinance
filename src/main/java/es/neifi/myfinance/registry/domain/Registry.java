package es.neifi.myfinance.registry.domain;

import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.shared.domain.AggregateRoot;
import es.neifi.myfinance.shared.domain.bus.event.AggregateID;
import es.neifi.myfinance.users.domain.UserID;

import java.util.Objects;

public class Registry extends AggregateRoot {

    private final UserID userId;
    private final RegistryID id;
    private final Category category;
    private final Name name;
    private final Cost cost;
    private final Currency currency;
    private final Date date;
    private final boolean isExpense;

    public Registry(UserID userId, RegistryID id, Category category, Name name, Cost cost, Currency currency, Date date, boolean isExpense) {
        this.userId = userId;
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

        registry.record(createdDomainEvent(userID, id, category, name, cost, currency, date, true));

        return registry;

    }

    private static RegistryCreatedDomainEvent createdDomainEvent(UserID userID, RegistryID id, Category category, Name name, Cost cost, Currency currency, Date date, boolean b) {
        return new RegistryCreatedDomainEvent(
                userID.value(),
                new AggregateID(id.value()),
                category.value(),
                name.value(),
                cost.value(),
                currency.value(),
                date.value(),
                b);
    }

    public static Registry createIncome(UserID userID, RegistryID id, Category category, Name name, Cost cost, Currency currency, Date date) {
        Registry registry = new Registry(userID, id, category, name, cost, currency, date, false);

        registry.record(createdDomainEvent(userID, id, category, name, cost, currency, date, false));

        return registry;
    }

    public UserID userId() {
        return userId;
    }

    public RegistryID id() {
        return id;
    }

    public Category category() {
        return category;
    }

    public Name name() {
        return name;
    }

    public Currency currency() {
        return currency;
    }

    public Date date() {
        return date;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public Cost cost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Registry registry = (Registry) o;
        return id.value().equalsIgnoreCase((registry.id.value()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.value());
    }
}
