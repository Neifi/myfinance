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

    public Date(String value){
        DateFormat dateFormat;
        if(value.split("-")[0].length() == 4){

             dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }else {
             dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        }
        dateFormat.setLenient(false);
        try {
            this.value = dateFormat.format(dateFormat.parse(value));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String value() {
        return this.value;
    }
}
