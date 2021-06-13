package es.neifi.myfinance.expenses.application.searchExpense;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistryResponse {

    private String id;
    private String category;
    private String name;
    private double money;
    private String currency;
    private String date;

}
