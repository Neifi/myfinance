package es.neifi.myfinance.expenses.domain.vo;

import es.neifi.myfinance.expenses.domain.exceptions.ExpenseCostException;
import es.neifi.myfinance.expenses.domain.vo.Date;
import es.neifi.myfinance.expenses.domain.vo.ExpenseCost;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseCostShould {

    @Test
    public void throw_exception_when_number_is_negative(){
        Exception exception = assertThrows(ExpenseCostException.class, () -> {
            ExpenseCost expense = new ExpenseCost(-40);
        });
    }

}