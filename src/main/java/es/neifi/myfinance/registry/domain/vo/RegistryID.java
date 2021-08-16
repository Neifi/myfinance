package es.neifi.myfinance.registry.domain.vo;

import es.neifi.myfinance.shared.domain.Identifier;
import es.neifi.myfinance.shared.domain.exception.EmptyValueException;

public class RegistryID extends Identifier {
    public RegistryID(String value) {
        super(value);
        if (value == null || value.isBlank()){
            throw new EmptyValueException("RegistryId cannot be empty");
        }
    }
}
