package es.neifi.myfinance.accountBalance.domain;

import es.neifi.myfinance.shared.domain.baseValueObject.DoubleValueObject;

public class Amount extends DoubleValueObject {
    public Amount(Double value) {
        super(value);
    }
}
