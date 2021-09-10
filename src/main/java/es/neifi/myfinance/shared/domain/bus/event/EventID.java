package es.neifi.myfinance.shared.domain.bus.event;

import es.neifi.myfinance.shared.domain.baseValueObject.IdentifierValueObject;

public class EventID extends IdentifierValueObject {
    public EventID(String value) {
        super(value);
    }
}
