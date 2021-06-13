package es.neifi.myfinance.registries.domain.vo;

import es.neifi.myfinance.registry.domain.exceptions.NegativeValueException;
import es.neifi.myfinance.registry.domain.vo.Cost;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistryCostShould {

    @Test
    public void throw_exception_when_number_is_negative(){
        Exception exception = assertThrows(NegativeValueException.class, () -> {
            Cost expense = new Cost(-40);
        });
    }

}