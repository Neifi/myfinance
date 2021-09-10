package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.reports.domain.IsExpense;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.reports.domain.TotalExpenses;
import es.neifi.myfinance.reports.domain.TotalIncomes;
import es.neifi.myfinance.reports.domain.TotalSavings;
import es.neifi.myfinance.users.domain.UserID;

import java.util.Optional;

public class ReportCalculator {

    private final ReportRepository reportRepository;
    private Optional<Report> last;
    private TotalSavings totalSavings = new TotalSavings(0);
    private TotalIncomes totalIncomes = new TotalIncomes(0);
    private TotalExpenses totalExpenses = new TotalExpenses(0);
    private Cost cost;

    public ReportCalculator(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report calculate(Registry registry){

        Report report;

        last = reportRepository.findLast(registry.userId().value());
        last.ifPresent(value -> {
            totalIncomes = value.totalIncomes();
            totalSavings = value.totalSavings();
            totalExpenses = value.totalExpenses();
        });

        if (registry.isExpense()) {
            report = calculateExpense(registry);
        } else {
            report = calculateIncome(registry);
        }

        return report;

    }

    private Report calculateIncome(Registry registry) {


        TotalIncomes newTotalIncomes = new TotalIncomes(totalIncomes.value() + registry.cost().value());
        TotalSavings newTotalSavings = new TotalSavings(totalSavings.value() + registry.cost().value());

        return Report.create(
                new ReportID(registry.id().value()),
                new UserID(registry.userId().value()),
                totalExpenses,
                newTotalIncomes,
                newTotalSavings,
                new IsExpense(false),
                new Date(registry.date().value())
        );
    }

    private Report calculateExpense(Registry registry){

        TotalExpenses newTotalExpenses = new TotalExpenses(totalExpenses.value() + registry.cost().value());
        TotalSavings newTotalSavings = new TotalSavings(totalSavings.value() - registry.cost().value());

        return Report.create(
                new ReportID(registry.id().value()),
                new UserID(registry.userId().value()),
                newTotalExpenses,
                totalIncomes,
                newTotalSavings,
                new IsExpense(true),
                new Date(registry.date().value())
        );


    }


}
