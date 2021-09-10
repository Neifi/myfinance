package es.neifi.myfinance.shared.domain.baseValueObject;

import java.util.UUID;

public class IdentifierValueObject extends BaseValueObject<String>{

    public IdentifierValueObject(String value) {
        super((String)value);
        isValidUUIDOrThrow(value);;
    }

    private void isValidUUIDOrThrow(String value) {
        UUID.fromString(value);
    }

}
