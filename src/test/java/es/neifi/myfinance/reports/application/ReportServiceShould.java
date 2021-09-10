package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.reports.domain.IsExpense;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.reports.domain.TotalExpenses;
import es.neifi.myfinance.reports.domain.TotalIncomes;
import es.neifi.myfinance.reports.domain.TotalSavings;
import es.neifi.myfinance.users.domain.UserID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportServiceShould {

    private ReportRepository reportRepository = Mockito.mock(ReportRepository.class);

    private ReportService reportService = new ReportService(reportRepository);

    @Test
    void return_existent_report() {
        String reportId = UUID.randomUUID().toString();
        Report report = Report.create(
                new ReportID(reportId),
                new UserID(UUID.randomUUID().toString()),
                new TotalExpenses(100D),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.valueOf(LocalDateTime.of(
                        2021,
                        6,
                        14,
                        15,
                        1)).getTime()));

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        Optional<Report> actual = reportService.findReport(reportId);

        Assertions.assertThat(actual).isEqualTo(Optional.of(report));
        verify(reportRepository,times(1)).findById(reportId);

    }

    @Test
    void return_empty_if_report_dont_exist() {

    }
}