package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.reports.domain.IsExpense;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.reports.domain.TotalExpenses;
import es.neifi.myfinance.reports.domain.TotalIncomes;
import es.neifi.myfinance.reports.domain.TotalSavings;

import java.text.ParseException;
import java.time.LocalDate;
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

    public Report calculate(Registry registry) throws ParseException {

        Report report;

        last = reportRepository.findLast();
        last.ifPresent(value -> {
            totalIncomes = value.getTotalIncomes();
            totalSavings = value.getTotalSavings();
            totalExpenses = value.getTotalExpenses();
        });

        if (registry.isExpense()) {
            report = calculateExpense(registry);
        } else {
            report = calculateIncome(registry);
        }

        return report;

    }


    private Report calculateIncome(Registry registry) throws ParseException {


        TotalIncomes newTotalIncomes = new TotalIncomes(totalIncomes.value() + registry.getCost().value());
        TotalSavings newTotalSavings = new TotalSavings(totalSavings.value() + registry.getCost().value());

        return Report.create(
                new ReportID(registry.getId().value()),
                totalExpenses,
                newTotalIncomes,
                newTotalSavings,
                new IsExpense(false),
                new Date(LocalDate.now().toString())
        );
    }

    private Report calculateExpense(Registry registry) throws ParseException {

        TotalExpenses newTotalExpenses = new TotalExpenses(totalExpenses.value() + registry.getCost().value());
        TotalSavings newTotalSavings = new TotalSavings(totalSavings.value() - registry.getCost().value());

        return Report.create(
                new ReportID(registry.getId().value()),
                newTotalExpenses,
                totalIncomes,
                newTotalSavings,
                new IsExpense(true),
                new Date(LocalDate.now().toString())
        );


    }


}