package es.neifi.myfinance.shared.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class DoubleValueObject {
    private double value = 0;

    public DoubleValueObject(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }
}
