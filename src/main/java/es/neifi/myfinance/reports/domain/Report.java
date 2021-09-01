package es.neifi.myfinance.reports.domain;

import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.users.domain.UserID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Builder
@Getter
@EqualsAndHashCode
public class Report {
    private ReportID reportId;
    private UserID userId;
    private TotalExpenses totalExpenses;
    private TotalIncomes totalIncomes;
    private TotalSavings totalSavings;
    private IsExpense isExpense;
    private Date date;

    private Report(ReportID reportId,UserID userId, TotalExpenses totalExpenses, TotalIncomes totalIncomes, TotalSavings totalSavings, IsExpense isExpense, Date date) {
        this.reportId = reportId;
        this.userId = userId;
        this.totalExpenses = totalExpenses;
        this.totalIncomes = totalIncomes;
        this.totalSavings = totalSavings;
        this.isExpense = isExpense;
        this.date = date;
    }

    public static Report create(ReportID reportID, UserID userId,TotalExpenses totalExpenses, TotalIncomes totalIncomes, TotalSavings totalSavings, IsExpense isExpense, Date date) {
        return new Report(reportID,userId,totalExpenses,totalIncomes,totalSavings,isExpense,date);
    }
}
