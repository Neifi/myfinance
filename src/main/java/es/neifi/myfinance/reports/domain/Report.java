package es.neifi.myfinance.reports.domain;

import es.neifi.myfinance.registry.domain.vo.Date;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class Report {
    private ReportID reportID;
    private double totalExpenses;
    private double totalIncomes;
    private double totalSavings;
    private boolean isExpense;
    private Date date;

    public Report(ReportID reportID, double totalExpenses, double totalIncomes, double totalSavings, boolean isExpense, Date date) {
        this.reportID = reportID;
        this.totalExpenses = totalExpenses;
        this.totalIncomes = totalIncomes;
        this.totalSavings = totalSavings;
        this.isExpense = isExpense;
        this.date = date;
    }
}
