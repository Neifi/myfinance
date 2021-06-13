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
    private Name name;
    private ExpenseCost expenseCost;
    private Currency currency;
    private Date date;

    public Expense(ExpenseID id, Category category, Name name, ExpenseCost expenseCost, Currency currency, Date date) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.expenseCost = expenseCost;
        this.currency = currency;
        this.date = date;
    }


    public double getCost() {
        return this.expenseCost.value();
    }
}
