package es.neifi.myfinance.expenses.domain;

import es.neifi.myfinance.expenses.domain.vo.Date;
import es.neifi.myfinance.expenses.domain.vo.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {

    void save(Expense expense);
    Optional<Expense> search(String id);

    List<Expense> search();

}
