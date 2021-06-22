package es.neifi.myfinance.savings.application;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SavingResponse {
    private String id;
    private double saved;
    private String currency;
    private String date;
}
