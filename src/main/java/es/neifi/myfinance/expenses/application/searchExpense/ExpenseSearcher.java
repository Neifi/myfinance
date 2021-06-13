package es.neifi.myfinance.expenses.application.searchExpense;

import es.neifi.myfinance.expenses.domain.ExpenseRepository;
import es.neifi.myfinance.shared.application.search.RegistrySearcher;
import es.neifi.myfinance.shared.domain.Service;

@Service
public class ExpenseSearcher extends RegistrySearcher {
    public ExpenseSearcher(ExpenseRepository expenseRepository) {
        super(expenseRepository);
    }
}
