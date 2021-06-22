package es.neifi.myfinance.reports.application;

import lombok.Builder;

@Builder
public class ReportResponse {

    private double totalExpenses;
    private double totalIncomes;
    private double totalSavings;
    private String date;


}
