package es.neifi.myfinance.incomes.domain;

import java.util.Optional;

public interface IncomeRepository {
    void save(Income income);
    Optional<Income> search(String id);
}
