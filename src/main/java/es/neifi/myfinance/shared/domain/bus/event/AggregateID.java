package es.neifi.myfinance.shared.domain.bus.event;

import es.neifi.myfinance.shared.domain.baseValueObject.IdentifierValueObject;

public class AggregateID extends IdentifierValueObject {
    public AggregateID(String value) {
        super(value);
    }
}
