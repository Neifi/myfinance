package es.neifi.myfinance.shared.application.calculations;

import es.neifi.myfinance.expenses.domain.vo.Expense;
import es.neifi.myfinance.incomes.domain.Income;

import java.util.List;

public class MoneyCalculator {

    public static double calculateExpenses(List<Expense> expenses){
        return expenses.stream().map(Expense::getCost)
                .reduce(0D, Double::sum);

    }
    public static double calculateIncomes(List<Income> incomes){
        return incomes.stream().map(Income::getCost)
                .reduce(0D, Double::sum);

    }

}
