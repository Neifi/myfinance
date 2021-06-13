package es.neifi.myfinance.registries.domain.vo;



import es.neifi.myfinance.registry.domain.vo.Date;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class DateShould {

   @Test
   public void throw_exception_when_date_param_is_invalid() throws ParseException {
       Exception exception = assertThrows(ParseException.class, () -> {
        Date badDate = new Date("40/33/2030");
       });
   }
}