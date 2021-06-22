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
    private Date date;



}
