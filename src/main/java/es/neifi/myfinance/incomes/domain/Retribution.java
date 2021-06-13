package es.neifi.myfinance.incomes.domain;

import es.neifi.myfinance.expenses.domain.exceptions.NegativeValueException;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Retribution {

    private double value;

    public Retribution(double value) {
        if(value < 0) throw new NegativeValueException("Retribution value cannot be less than zero");
        this.value = value;
    }

    public double value(){
        return this.value;
    }
}
