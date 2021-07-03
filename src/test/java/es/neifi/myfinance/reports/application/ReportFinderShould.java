package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.reports.domain.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportFinderShould {

    private final ReportRepository reportRepository = Mockito.mock(ReportRepository.class);

    private ReportFinder reportFinder = new ReportFinder(reportRepository);

    @Test
    void find_all_reports() throws ParseException {

        Report report = Report.create(
                new ReportID("d26b3d48-beeb-46ca-82cc-5d5b23285447"),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date("27/11/2021"));

        Report report2 = Report.create(
                new ReportID("a12578f5-fdca-4733-9973-9eaa6c9f3ce8"),
                new TotalExpenses(150),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date("27/11/2021"));

        List<Report> expectedReports = List.of(report,report2);
        when(reportRepository.search()).thenReturn(expectedReports);
        List<Report> reportList = reportFinder.findAll();

        verify(reportRepository,times(1)).search();
        assertFalse(reportList.isEmpty());
        assertTrue(reportList.contains(report));
        assertTrue(reportList.contains(report2));
    }

    @Test
    void find_report_by_id() throws ParseException {
        String id = "d26b3d48-beeb-46ca-82cc-5d5b23285447";
        Report report = Report.create(
                new ReportID(id),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date("27/11/2021"));

        when(reportRepository.findById(id)).thenReturn(Optional.of(report));

        reportFinder.findById(id);

        verify(reportRepository,times(1)).findById(id);
    }

}