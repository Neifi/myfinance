package es.neifi.myfinance.expenses.application.searchExpense;

import es.neifi.myfinance.expenses.domain.ExpenseRepository;
import es.neifi.myfinance.expenses.domain.vo.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseSearcherShould {

    private final ExpenseRepository expenseRepository = Mockito.mock(ExpenseRepository.class);
    private ExpenseSearcher expenseSearcher = new ExpenseSearcher(expenseRepository);

    @Test
    void search_one_expense() throws ParseException {
        String id = "22aa0d3b-07eb-4f19-8320-1b3c3a25b070";
        Expense expectedExpense = Expense.builder()
                .id(new ExpenseID(id))
                .category(new Category("home"))
                .name(new Name("internet"))
                .expenseCost(new ExpenseCost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Mockito.when(expenseRepository.search(id)).thenReturn(Optional.ofNullable(expectedExpense));
        Optional<Expense> expense = expenseSearcher.search(id);

        assertTrue(expense.isPresent());
        assertEquals(id,expense.get().getId().value());
    }

    @Test
    void search_all_expenses() throws ParseException {
        Expense expense = Expense.builder()
                .id(new ExpenseID("787f28f2-003a-4445-8659-d60683107845"))
                .category(new Category("home"))
                .name(new Name("internet"))
                .expenseCost(new ExpenseCost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Expense expense2 = Expense.builder()
                .id(new ExpenseID("cb99dff2-9f0a-4bb7-88a8-4b3b5937e6c5"))
                .category(new Category("home"))
                .name(new Name("invoice"))
                .expenseCost(new ExpenseCost(110))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Expense expense3 = Expense.builder()
                .id(new ExpenseID("f3b97578-4c18-4753-a4d5-364f0099423b"))
                .category(new Category("home"))
                .name(new Name("invoice2"))
                .expenseCost(new ExpenseCost(50))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();

        List<Expense> expectedExpenses = new ArrayList<>();
        expectedExpenses.add(expense);
        expectedExpenses.add(expense2);
        expectedExpenses.add(expense3);

        Mockito.when(expenseRepository.search()).thenReturn(expectedExpenses);

        List<Expense> expenses = expenseSearcher.search();

        assertEquals(3,expenses.size());
        assertEquals(expectedExpenses,expenses);
    }

    @Test
    void search_all_expenses_between_dates_in_order() throws ParseException {
        Expense expense = Expense.builder()
                .id(new ExpenseID("787f28f2-003a-4445-8659-d60683107845"))
                .category(new Category("home"))
                .name(new Name("internet"))
                .expenseCost(new ExpenseCost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Expense expense2 = Expense.builder()
                .id(new ExpenseID("cb99dff2-9f0a-4bb7-88a8-4b3b5937e6c5"))
                .category(new Category("home"))
                .name(new Name("invoice"))
                .expenseCost(new ExpenseCost(110))
                .currency(new Currency("EUR"))
                .date(new Date("07/06/2021"))
                .build();
        Expense expense3 = Expense.builder()
                .id(new ExpenseID("f3b97578-4c18-4753-a4d5-364f0099423b"))
                .category(new Category("home"))
                .name(new Name("invoice2"))
                .expenseCost(new ExpenseCost(50))
                .currency(new Currency("EUR"))
                .date(new Date("06/06/2021"))
                .build();
        Expense expense4 = Expense.builder()
                .id(new ExpenseID("80d729f2-be4a-4228-a9ef-3c140357abe1"))
                .category(new Category("home"))
                .name(new Name("invoice3"))
                .expenseCost(new ExpenseCost(50))
                .currency(new Currency("EUR"))
                .date(new Date("08/05/2021"))
                .build();
        Expense expense5 = Expense.builder()
                .id(new ExpenseID("9f0c6936-67b7-4793-ad08-c3c3197952b6"))
                .category(new Category("home"))
                .name(new Name("invoice4"))
                .expenseCost(new ExpenseCost(50))
                .currency(new Currency("EUR"))
                .date(new Date("08/05/2020"))
                .build();
        List<Expense> expectedExpenses = new ArrayList<>();
        expectedExpenses.add(expense);
        expectedExpenses.add(expense2);
        expectedExpenses.add(expense3);
        expectedExpenses.add(expense4);
        expectedExpenses.add(expense5);

        Mockito.when(expenseRepository.search()).thenReturn(expectedExpenses);

        List<Expense> expenses = expenseSearcher.search("01/06/2021","30/06/2021");

        assertEquals(3,expenses.size());
        assertEquals(expectedExpenses.get(0),expenses.get(0));
        assertEquals(expectedExpenses.get(1),expenses.get(1));
        assertEquals(expectedExpenses.get(2),expenses.get(2));
    }

    @Test
    void return_empty_list_when_no_expenses_between_dates() {
        List<Expense> expenses = expenseSearcher.search("01/06/2021","30/06/2021");
        assertTrue(expenses.isEmpty());
    }
}