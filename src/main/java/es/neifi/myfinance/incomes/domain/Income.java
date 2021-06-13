package es.neifi.myfinance.incomes.domain;

import es.neifi.myfinance.expenses.domain.vo.Category;
import es.neifi.myfinance.expenses.domain.vo.Currency;
import es.neifi.myfinance.expenses.domain.vo.Date;
import es.neifi.myfinance.expenses.domain.vo.Name;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Income  {
    private IncomeID incomeID;
    private Category category;
    private Name name;
    private Retribution retribution;
    private Currency currency;
    private Date date;

    public Income(IncomeID incomeID, Category category, Name name, Retribution retribution, Currency currency, Date date) {
        this.incomeID = incomeID;
        this.category = category;
        this.name = name;
        this.retribution = retribution;
        this.currency = currency;
        this.date = date;
    }

    public double getCost() {
        return this.retribution.value();
    }
}
