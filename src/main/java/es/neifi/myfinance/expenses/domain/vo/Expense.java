package es.neifi.myfinance.expenses.domain.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Builder
public class Expense {
    private ExpenseID id;
    private Category category;
    private ExpenseName name;
    private ExpenseCost cost;
    private Currency currency;
    private Date date;

    public Expense(ExpenseID id, Category category, ExpenseName name, ExpenseCost cost, Currency currency, Date date) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.currency = currency;
        this.date = date;
    }
}
