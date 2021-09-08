package es.neifi.myfinance.registry.domain.vo;


import org.junit.jupiter.api.Test;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DateShould {

    @Test
    public void set_valid_date() {

        Date date = new Date(valueOf(of(
                2021,
                6,
                14,
                15,
                1)).getTime());

        assertNotNull(date);

        assertEquals(valueOf(of(
                2021,
                6,
                14,
                15,
                1)).getTime(), date.value());

    }

}