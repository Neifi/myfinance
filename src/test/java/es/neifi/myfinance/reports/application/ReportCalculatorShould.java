package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.reports.domain.IsExpense;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.reports.domain.TotalExpenses;
import es.neifi.myfinance.reports.domain.TotalIncomes;
import es.neifi.myfinance.reports.domain.TotalSavings;
import es.neifi.myfinance.users.domain.UserID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

class ReportCalculatorShould {

    private ReportRepository reportRepository = Mockito.mock(ReportRepository.class);
    private ReportCalculator reportCalculator = new ReportCalculator(reportRepository);

    @Test
    void calculate_data_correctly_for_expense_report_when_registry_is_created_with_existent_last_report() throws ParseException {
        String id = "24985754-13bc-4bee-a972-d37e81fcb9ff";
        String userId = UUID.randomUUID().toString();

        Report lastReport = Report.create(
                new ReportID(userId),
                new UserID(UUID.randomUUID().toString()),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime()));

        Report expectedExpenseReport = Report.create(
                new ReportID(id),
                new UserID(userId),
                new TotalExpenses(200.0),
                new TotalIncomes(1000.0),
                new TotalSavings(800.0),
                new IsExpense(true),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime()));

        Registry expenseRegistry = Registry.createExpense(
                new UserID(userId),
                new RegistryID(id),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime())
        );

        Mockito.when(reportRepository.findLast(userId)).thenReturn(Optional.of(lastReport));

        Report actual = reportCalculator.calculate(expenseRegistry);

        Mockito.verify(reportRepository, times(1)).findLast(userId);
        assertEquals(expectedExpenseReport.getReportId(), actual.getReportId());
        assertEquals(expectedExpenseReport.getIsExpense(), actual.getIsExpense());
        assertEquals(expectedExpenseReport.getTotalIncomes(), actual.getTotalIncomes());
        assertEquals(expectedExpenseReport.getTotalExpenses(), actual.getTotalExpenses());
        assertEquals(expectedExpenseReport.getTotalSavings(), actual.getTotalSavings());

    }

    @Test
    void calculate_data_correctly_for_income_report_when_registry_is_created_with_existent_last_report() throws ParseException {
        String id = "24985754-13bc-4bee-a972-d37e81fcb9ff";
        String userId = UUID.randomUUID().toString();
        Report lastReport = Report.create(
                new ReportID("d26b3d48-beeb-46ca-82cc-5d5b23285447"),
                new UserID(userId),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime()));

        Report expectedExpenseReport = Report.create(
                new ReportID(id),
                new UserID(userId),
                new TotalExpenses(100.0),
                new TotalIncomes(1100.0),
                new TotalSavings(1000.0),
                new IsExpense(false),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime()));

        Registry expenseRegistry = Registry.createIncome(
                new UserID(userId),
                new RegistryID(id),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime())
        );

        Mockito.when(reportRepository.findLast(userId)).thenReturn(Optional.of(lastReport));

        Report actual = reportCalculator.calculate(expenseRegistry);

        Mockito.verify(reportRepository, times(1)).findLast(userId);

        assertEquals(expectedExpenseReport.getReportId(), actual.getReportId());
        assertEquals(expectedExpenseReport.getIsExpense(), actual.getIsExpense());
        assertEquals(expectedExpenseReport.getTotalIncomes(), actual.getTotalIncomes());
        assertEquals(expectedExpenseReport.getTotalExpenses(), actual.getTotalExpenses());
        assertEquals(expectedExpenseReport.getTotalSavings(), actual.getTotalSavings());

    }

    @Test
    void calculate_data_correctly_for_income_report_when_registry_is_created_with_no_last_report() throws ParseException {
        String id = "24985754-13bc-4bee-a972-d37e81fcb9ff";
        String userId = UUID.randomUUID().toString();

        Report expectedIncomeReport = Report.create(
                new ReportID(id),
                new UserID(UUID.randomUUID().toString()),
                new TotalExpenses(0),
                new TotalIncomes(100.0),
                new TotalSavings(100.0),
                new IsExpense(false),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime()));

        Registry incomeRegistry = Registry.createIncome(
                new UserID(userId),
                new RegistryID(id),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime())
        );

        Mockito.when(reportRepository.findLast(userId)).thenReturn(Optional.empty());

        Report actual = reportCalculator.calculate(incomeRegistry);

        Mockito.verify(reportRepository, times(1)).findLast(userId);
        assertEquals(expectedIncomeReport.getReportId(), actual.getReportId());
        assertEquals(expectedIncomeReport.getIsExpense(), actual.getIsExpense());
        assertEquals(expectedIncomeReport.getTotalIncomes(), actual.getTotalIncomes());
        assertEquals(expectedIncomeReport.getTotalExpenses(), actual.getTotalExpenses());
        assertEquals(expectedIncomeReport.getTotalSavings(), actual.getTotalSavings());
    }

    @Test
    void calculate_data_correctly_for_expense_report_when_registry_is_created_with_no_last_report() throws ParseException {
        String id = "24985754-13bc-4bee-a972-d37e81fcb9ff";
        String userId = UUID.randomUUID().toString();

        Report expectedExpenseReport = Report.create(
                new ReportID(id),
                new UserID(userId),
                new TotalExpenses(100.0),
                new TotalIncomes(0.0),
                new TotalSavings(0.0),
                new IsExpense(true),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime()));

        Registry expenseRegistry = Registry.createExpense(
                new UserID(userId),
                new RegistryID(id),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime())
        );

        Mockito.when(reportRepository.findLast(userId)).thenReturn(Optional.empty());

        Report actual = reportCalculator.calculate(expenseRegistry);

        Mockito.verify(reportRepository, times(1)).findLast(userId);
        assertEquals(expectedExpenseReport.getReportId(), actual.getReportId());
        assertEquals(expectedExpenseReport.getIsExpense(), actual.getIsExpense());
        assertEquals(expectedExpenseReport.getTotalIncomes(), actual.getTotalIncomes());
        assertEquals(expectedExpenseReport.getTotalExpenses(), actual.getTotalExpenses());
        assertEquals(expectedExpenseReport.getTotalSavings(), actual.getTotalSavings());

    }

}