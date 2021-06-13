package es.neifi.myfinance.expenses.application.searchExpense;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ExpenseListResponse {
    private List<RegistryResponse> expenses;
    private double totalExpended;
    private String[] timePeriod;
}
