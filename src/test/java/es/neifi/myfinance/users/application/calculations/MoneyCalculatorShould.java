package es.neifi.myfinance.users.application.calculations;

import es.neifi.myfinance.expenses.domain.vo.*;
import es.neifi.myfinance.shared.application.calculations.MoneyCalculator;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoneyCalculatorShould {

    @Test
    void calculate_total_amount() throws ParseException {
        Expense expense = Expense.builder()
                .id(new ExpenseID("787f28f2-003a-4445-8659-d60683107845"))
                .category(new Category("home"))
                .name(new Name("internet"))
                .expenseCost(new ExpenseCost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Expense expense1 = Expense.builder()
                .id(new ExpenseID("787f28f2-003a-4445-8659-d60683107845"))
                .category(new Category("home"))
                .name(new Name("internet"))
                .expenseCost(new ExpenseCost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Expense expense2 = Expense.builder()
                .id(new ExpenseID("787f28f2-003a-4445-8659-d60683107845"))
                .category(new Category("home"))
                .name(new Name("internet"))
                .expenseCost(new ExpenseCost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();

        List<Expense> expenses = Arrays.asList(expense, expense1, expense2);

        double total = MoneyCalculator.calculateExpenses(expenses);

        assertEquals(300,total);
    }

}