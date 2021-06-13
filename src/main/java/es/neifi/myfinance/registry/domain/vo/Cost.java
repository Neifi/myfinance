package es.neifi.myfinance.registry.domain.vo;

import es.neifi.myfinance.registry.domain.exceptions.NegativeValueException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Cost {

    private double value;

    public Cost(double value) {
        if(value < 0) throw new NegativeValueException("Value cannot be less than zero");
        this.value = value;
    }

    public double value(){
        return this.value;
    }
}
