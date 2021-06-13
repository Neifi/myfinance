package es.neifi.myfinance.incomes.infrastructure;

import es.neifi.myfinance.incomes.domain.Income;
import es.neifi.myfinance.incomes.domain.IncomeRepository;
import es.neifi.myfinance.shared.domain.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class InMemoryIncomeRepository implements IncomeRepository {

    private HashMap<String, Income> incomes = new HashMap<>();

    @Override
    public void save(Income income) {
        incomes.put(income.getIncomeID().value(), income);
    }

    @Override
    public Optional<Income> search(String id) {
        return Optional.ofNullable(incomes.get(id));
    }
}
