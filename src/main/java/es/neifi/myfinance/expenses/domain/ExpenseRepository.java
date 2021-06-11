package es.neifi.myfinance.expenses.domain;

import es.neifi.myfinance.expenses.domain.vo.Expense;

import java.util.Optional;

public interface ExpenseRepository {

    void save(Expense expense);
    Optional<Expense> search(String id);
}
