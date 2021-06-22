package es.neifi.myfinance.savings.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Saving {
    private String id;
    private double saved;
    private String currency;
    private String date;
}
