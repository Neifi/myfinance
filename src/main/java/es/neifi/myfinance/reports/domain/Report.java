package es.neifi.myfinance.reports.domain;

import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.users.domain.UserID;

import java.util.Objects;

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

    public ReportID reportId() {
        return reportId;
    }

    public UserID userId() {
        return userId;
    }

    public TotalExpenses totalExpenses() {
        return totalExpenses;
    }

    public TotalIncomes totalIncomes() {
        return totalIncomes;
    }

    public TotalSavings totalSavings() {
        return totalSavings;
    }

    public IsExpense isExpense() {
        return isExpense;
    }

    public Date date() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return reportId.value().equalsIgnoreCase(report.reportId.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId);
    }
}
