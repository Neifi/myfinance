package es.neifi.myfinance.registry.domain.vo;

import es.neifi.myfinance.registry.domain.exceptions.NegativeValueException;
import es.neifi.myfinance.shared.domain.baseValueObject.DoubleValueObject;


public class Cost extends DoubleValueObject {

    public Cost(double value) {
        super(value);
        if (value < 0) throw new NegativeValueException("Value cannot be less than zero");
    }

}
