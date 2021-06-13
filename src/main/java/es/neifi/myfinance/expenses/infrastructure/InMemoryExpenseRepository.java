package es.neifi.myfinance.expenses.infrastructure;

import es.neifi.myfinance.expenses.domain.vo.Date;
import es.neifi.myfinance.expenses.domain.vo.Expense;
import es.neifi.myfinance.expenses.domain.ExpenseRepository;
import es.neifi.myfinance.shared.domain.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InMemoryExpenseRepository implements ExpenseRepository {

    private HashMap<String, Expense> expenses = new HashMap<>();

    @Override
    public void save(Expense expense) {
        expenses.put(expense.getId().value(), expense);
    }

    @Override
    public Optional<Expense> search(String id) {
        return Optional.ofNullable(expenses.get(id));
    }

    @Override
    public List<Expense> search() {
        return new ArrayList<>(expenses.values());
    }

}
