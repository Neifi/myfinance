package es.neifi.myfinance.expenses.application.saveExpense;

import es.neifi.myfinance.expenses.domain.ExpenseRepository;
import es.neifi.myfinance.expenses.domain.vo.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;

class ExpenseSaverShould {

    private ExpenseRepository expenseRepository = Mockito.mock(ExpenseRepository.class);

    private ExpenseSaver expenseSaver = new ExpenseSaver(expenseRepository);

    @Test
    public void save_expense_successfully() throws ParseException {
        SaveExpenseRequest request = new SaveExpenseRequest(
                "1711BB48-008E-4041-AE6F-201DDED5A5B7",
                "some-cat","some-name",100,"EUR","08/10/2021");
        expenseSaver.save(request);

        Expense expense = Expense.builder()
                .category(new Category(request.category()))
                .name(new Name(request.name()))
                .expenseCost(new ExpenseCost(request.cost()))
                .currency(new Currency(request.currency()))
                .date(new Date(request.date()))
                .build();

        Mockito.verify(expenseRepository,Mockito.times(1)).save(expense);
    }

}