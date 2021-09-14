package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.reports.application.exceptions.NoReportFoundException;
import es.neifi.myfinance.reports.domain.IsExpense;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.reports.domain.TotalExpenses;
import es.neifi.myfinance.reports.domain.TotalIncomes;
import es.neifi.myfinance.reports.domain.TotalSavings;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.application.exceptions.UserNotFoundException;
import es.neifi.myfinance.users.domain.UserID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportFinderShould {

    private final ReportRepository reportRepository = Mockito.mock(ReportRepository.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final ReportFinder reportFinder = new ReportFinder(reportRepository, userService);

    @Test
    void find_all_reports_for_given_user_id() {

        String userId = UUID.randomUUID().toString();
        Report report = Report.create(
                new ReportID("d26b3d48-beeb-46ca-82cc-5d5b23285447"),
                new UserID(userId),
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
                new UserID(userId),
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
        when(reportRepository.search(userId)).thenReturn(expectedReports);
        List<Report> reportList = reportFinder.findAll(userId);

        verify(reportRepository, times(1)).search(userId);
        assertFalse(reportList.isEmpty());
        assertTrue(reportList.contains(report));
        assertTrue(reportList.contains(report2));
    }

    @Test
    void find_last_report() {
        String id = "d26b3d48-beeb-46ca-82cc-5d5b23285447";
        String userId = "47080dd8-587b-441f-9754-9013cc6f64f6";

        Report lastReport = Report.create(
                new ReportID(id),
                new UserID(userId),
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

        when(reportRepository.findLast(userId)).thenReturn(Optional.of(lastReport));

        reportFinder.findLast(userId);

        verify(reportRepository, times(1)).findLast(userId);
    }

    @Test
    void throw_user_not_found_exception_when_associated_user_is_not_found() {
        String id = "d26b3d48-beeb-46ca-82cc-5d5b23285447";
        String userId = "b26b3d48-beeb-46ca-82cc-5d5b23285448";
        when(userService.find(userId)).thenThrow(new UserNotFoundException(id));
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            reportFinder.findById(userId, id);
        });
    }

    @Test
    void throw_report_not_found_exception_when_report_is_not_found() {
        String reportId = UUID.randomUUID().toString();
        String userId = "b26b3d48-beeb-46ca-82cc-5d5b23285448";
        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoReportFoundException.class, () -> {
            reportFinder.findById(userId, reportId);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("No report found with ID: "+reportId);

    }

    @Test
    void throw_reports_not_found_exception_when_report_is_not_found() {
        String reportId = UUID.randomUUID().toString();
        String userId = "b26b3d48-beeb-46ca-82cc-5d5b23285448";
        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoReportFoundException.class, () -> {
            reportFinder.findAll(userId);
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo("No reports found");
    }

}