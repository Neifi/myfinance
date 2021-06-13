package es.neifi.myfinance.registry.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@EqualsAndHashCode
@Getter
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
