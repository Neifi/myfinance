package es.neifi.myfinance.incomes.domain;

import es.neifi.myfinance.expenses.domain.exceptions.NegativeValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RetributionShould {

    @Test
    void throwException_when_retribution_param_is_less_than_zero(){
        Exception exception = assertThrows(NegativeValueException.class, () -> {
            Retribution retribution = new Retribution(-40);
        });
    }
}