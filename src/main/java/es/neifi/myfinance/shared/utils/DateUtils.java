package es.neifi.myfinance.shared.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateUtils {

    public static Long timestampOf(int year, int month, int dayOfMonth, int hour, int minute) {
        return Timestamp.valueOf(LocalDateTime.of(
                year,
                month,
                dayOfMonth,
                hour,
                minute)).getTime();
    }
}
