package es.neifi.myfinance.incomes.application.IncomeSaver;

import es.neifi.myfinance.incomes.domain.Income;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveIncomeRequest {
    private String id;
    private String category;
    private String name;
    private double retribution;
    private String currency;
    private String date;

    public SaveIncomeRequest(String id, String category, String name, double retribution, String currency, String date) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.retribution = retribution;
        this.currency = currency;
        this.date = date;
    }
}
