package es.neifi.myfinance.users.domain;

import es.neifi.myfinance.expenses.domain.vo.ExpenseCost;
import es.neifi.myfinance.users.domain.exceptions.InvalidEmailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailShould {

    @Test
    public void throw_exception_when_email_param_is_invalid(){
        Exception exception = assertThrows(InvalidEmailException.class, () -> {
            Email badEmail = new Email("test");
        });
    }

}