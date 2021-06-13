package es.neifi.myfinance.expenses.application.saveExpense;

import es.neifi.myfinance.expenses.domain.ExpenseRepository;
import es.neifi.myfinance.expenses.domain.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class ExpenseSaver {

    @Autowired
    private ExpenseRepository expenseRepository;

    public ExpenseSaver(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void save(SaveExpenseRequest request) throws ParseException {
        Expense expense = Expense.builder()
                .category(new Category(request.category()))
                .name(new Name(request.name()))
                .expenseCost(new ExpenseCost(request.cost()))
                .currency(new Currency(request.currency()))
                .date(new Date(request.date()))
                .build();
        expenseRepository.save(expense);
    }
}
