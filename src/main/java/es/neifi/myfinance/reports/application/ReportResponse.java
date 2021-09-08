package es.neifi.myfinance.reports.application;

public class ReportResponse {

    private Double totalExpenses;
    private Double totalIncomes;
    private Double totalSavings;
    private boolean isExpense;
    private Long date;

    public ReportResponse(Double totalExpenses, Double totalIncomes, Double totalSavings, Boolean isExpense, Long date) {
        this.totalExpenses = totalExpenses;
        this.totalIncomes = totalIncomes;
        this.totalSavings = totalSavings;
        this.isExpense = isExpense;
        this.date = date;
    }


    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public Double getTotalIncomes() {
        return totalIncomes;
    }

    public Double getTotalSavings() {
        return totalSavings;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public Long getDate() {
        return date;
    }

}
