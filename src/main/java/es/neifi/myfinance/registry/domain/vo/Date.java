package es.neifi.myfinance.registry.domain.vo;

import es.neifi.myfinance.shared.domain.exception.EmptyValueException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;

@EqualsAndHashCode
@Getter
public class Date {
    private final Long value;

    public Date(Long value) {
        if (value == null) {
            throw new EmptyValueException("Date cannot be empty");
        }
        this.value = new Timestamp(value).getTime();

    }

    public Date() {
        this.value = Timestamp.from(Instant.now()).getTime();
    }

    public Long value() {
        return this.value;
    }
}
