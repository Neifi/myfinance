package es.neifi.myfinance.registry.domain.vo;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DateShould {

   @Test
   public void dont_set_invalid_date() {

        Date badDate = new Date(312937819L);

       Assertions.assertNull(badDate.value());

   }
   @Test
   public void set_valid_date() {

        Date date = new Date(Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                14,
                15,
                1)).getTime());

       assertNotNull(date);
       assertEquals(Timestamp.valueOf(LocalDateTime.of(
               2021,
               6,
               14,
               15,
               1)).getTime(),date.value());

   }
}