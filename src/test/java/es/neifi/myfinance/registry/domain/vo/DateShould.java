package es.neifi.myfinance.registry.domain.vo;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DateShould {

   @Test
   public void dont_set_invalid_date() {

        Date badDate = new Date("40/33/2030");

       Assertions.assertNull(badDate.value());

   }
   @Test
   public void set_valid_date() {

        Date date = new Date("07/03/2030");

       assertNotNull(date);
       assertEquals("07/03/2030",date.value());

   }
}