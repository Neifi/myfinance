package es.neifi.myfinance.expenses.domain.vo;

import lombok.EqualsAndHashCode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@EqualsAndHashCode
public class Date {
    private String value;

    public Date(String value) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
            this.value = dateFormat.format(dateFormat.parse(value));
    }

    public String value() {
        return this.value;
    }
}
