package es.neifi.myfinance.reports.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class TotalSavings {
    private double value = 0;
    public TotalSavings(double value) {
        if (value >= 0){
            this.value = value;
        }
    }

    public double value() {
        return value;
    }
}
