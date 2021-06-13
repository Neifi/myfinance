package es.neifi.myfinance.expenses.domain.vo;

import es.neifi.myfinance.expenses.domain.exceptions.NegativeValueException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class ExpenseCost{

    private double value;

    public ExpenseCost(double value) {
        if(value < 0) throw new NegativeValueException("Value cannot be less than zero");
        this.value = value;
    }

    public double value(){
        return this.value;
    }
}