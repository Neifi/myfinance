package es.neifi.myfinance.expenses.infrastructure;

import es.neifi.myfinance.expenses.domain.ExpenseRepository;
import es.neifi.myfinance.expenses.domain.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryExpenseRepositoryShould {

    private ExpenseRepository inMemoryExpenseRepository;

    @BeforeEach
    public void setup(){
        inMemoryExpenseRepository = new InMemoryExpenseRepository();
    }

    @Test
    void save_expense_successfully_in_db() throws ParseException {
        Expense expense = Expense.builder()
                .id(new ExpenseID("77335C39-B709-4176-989D-9800216DC0C7"))
                .category(new Category("Home"))
                .name(new ExpenseName("Desktop"))
                .cost(new ExpenseCost(100.40))
                .currency(new Currency("EUR"))
                .date(new Date("12/6/2021"))
                .build();

        inMemoryExpenseRepository.save(expense);
    }

    @Test
    void search_expense_with_existent_id() throws ParseException {
        Expense expectedExpense = Expense.builder()
                .id(new ExpenseID("77335C39-B709-4176-989D-9800216DC0C7"))
                .category(new Category("Home"))
                .name(new ExpenseName("Desktop"))
                .cost(new ExpenseCost(100.40))
                .currency(new Currency("EUR"))
                .date(new Date("12/06/2021"))
                .build();

        inMemoryExpenseRepository.save(expectedExpense);
        Optional<Expense> expense = inMemoryExpenseRepository.search("77335C39-B709-4176-989D-9800216DC0C7".toLowerCase());

        assertTrue(expense.isPresent());
        assertEquals(expense,Optional.of(expectedExpense));

    }

    @Test
    void not_return_non_existing_expense() {

        Optional<Expense> expense = inMemoryExpenseRepository.search("non-existing-id".toLowerCase());

        assertFalse(expense.isPresent());

    }
}