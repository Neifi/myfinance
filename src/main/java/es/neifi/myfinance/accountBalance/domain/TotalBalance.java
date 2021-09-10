package es.neifi.myfinance.accountBalance.domain;

import es.neifi.myfinance.shared.domain.baseValueObject.DoubleValueObject;

public class TotalBalance extends DoubleValueObject {
    public TotalBalance(double value) {
        super(value);
    }
}
