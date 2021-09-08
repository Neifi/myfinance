package es.neifi.myfinance.shared.domain;

import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.shared.domain.exception.EmptyValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BaseValueObjectShould {

    @Test
    void throw_exception_when_null_arg_is_given() {

        Exception exception = assertThrows(EmptyValueException.class, () -> {
            Date badDate = new Date(null);
        });
    }

}