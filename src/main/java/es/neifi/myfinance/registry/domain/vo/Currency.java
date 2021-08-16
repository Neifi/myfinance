package es.neifi.myfinance.registry.domain.vo;

import es.neifi.myfinance.shared.domain.exception.EmptyValueException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Currency {
    private String value;

    public Currency(String value) {
        if (value == null) {
            throw new EmptyValueException("Currency cannot be empty");
        }
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
