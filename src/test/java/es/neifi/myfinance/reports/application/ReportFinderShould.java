package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.reports.domain.IsExpense;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.reports.domain.TotalExpenses;
import es.neifi.myfinance.reports.domain.TotalIncomes;
import es.neifi.myfinance.reports.domain.TotalSavings;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportFinderShould {

    private final ReportRepository reportRepository = Mockito.mock(ReportRepository.class);

    private final ReportFinder reportFinder = new ReportFinder(reportRepository);

    @Test
    void find_all_reports() {

        Report report = Report.create(
                new ReportID("d26b3d48-beeb-46ca-82cc-5d5b23285447"),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime()));

        Report report2 = Report.create(
                new ReportID("a12578f5-fdca-4733-9973-9eaa6c9f3ce8"),
                new TotalExpenses(150),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime()));

        List<Report> expectedReports = List.of(report, report2);
        when(reportRepository.search()).thenReturn(expectedReports);
        List<Report> reportList = reportFinder.findAll();

        verify(reportRepository, times(1)).search();
        assertFalse(reportList.isEmpty());
        assertTrue(reportList.contains(report));
        assertTrue(reportList.contains(report2));
    }

    @Test
    void find_report_by_id() {
        String id = "d26b3d48-beeb-46ca-82cc-5d5b23285447";
        Report report = Report.create(
                new ReportID(id),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime()));

        when(reportRepository.findById(id)).thenReturn(Optional.of(report));

        reportFinder.findById(id);

        verify(reportRepository, times(1)).findById(id);
    }
}