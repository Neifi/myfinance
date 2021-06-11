package es.neifi.myfinance.expenses.domain.vo;

import es.neifi.myfinance.expenses.domain.exceptions.ExpenseCostException;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ExpenseCost{

    private double value;

    public ExpenseCost(double value) {
        if(value < 0) throw new ExpenseCostException("Value cannot be less than zero");
        this.value = value;
    }

    public double value(){
        return this.value;
    }
}
