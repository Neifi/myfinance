package es.neifi.myfinance.reports.domain;

import es.neifi.myfinance.registry.domain.vo.Date;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Builder
@Getter
@EqualsAndHashCode
public class Report {
    private ReportID reportID;
    private TotalExpenses totalExpenses;
    private TotalIncomes totalIncomes;
    private TotalSavings totalSavings;
    private IsExpense isExpense;
    private Date date;

    public Report(ReportID reportID, TotalExpenses totalExpenses, TotalIncomes totalIncomes, TotalSavings totalSavings, IsExpense isExpense, Date date) {
        this.reportID = reportID;
        this.totalExpenses = totalExpenses;
        this.totalIncomes = totalIncomes;
        this.totalSavings = totalSavings;
        this.isExpense = isExpense;
        this.date = date;
    }

    public static Report create(ReportID reportID, TotalExpenses totalExpenses, TotalIncomes totalIncomes, TotalSavings totalSavings, IsExpense isExpense, Date date) {
        return new Report(reportID,totalExpenses,totalIncomes,totalSavings,isExpense,date);
    }
}
