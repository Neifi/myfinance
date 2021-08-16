package es.neifi.myfinance.registry.domain.vo;

import es.neifi.myfinance.shared.domain.exception.EmptyValueException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Date {
    private final Long value;

    public Date(Long value){
        if (value == null){
            throw new EmptyValueException("Date cannot be empty");
        }
        this.value = value;
    }

    public Long value() {
        return this.value;
    }
}
