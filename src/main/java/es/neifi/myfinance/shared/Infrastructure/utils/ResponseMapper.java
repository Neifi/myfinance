package es.neifi.myfinance.shared.Infrastructure.utils;

import es.neifi.myfinance.expenses.application.searchExpense.RegistryResponse;
import es.neifi.myfinance.expenses.domain.vo.Expense;

import java.util.List;

public class ResponseMapper {

    public static List<Expense> mapToExpenseResponse(String initialDate, String endDate, List<RegistryResponse> registryResponse, List<Expense> expenses) {

        for (Expense expense : expenses) {
            registryResponse.add(RegistryResponse.builder()
                    .id(expense.getId().value())
                    .category(expense.getCategory().value())
                    .name(expense.getName().value())
                    .money(expense.getExpenseCost().value())
                    .currency(expense.getCurrency().getValue())
                    .date(expense.getDate().getValue())
                    .build());
        }
        return expenses;
    }
}
