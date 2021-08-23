package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.MyfinanceApplication;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.reports.domain.IsExpense;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.reports.domain.TotalExpenses;
import es.neifi.myfinance.reports.domain.TotalIncomes;
import es.neifi.myfinance.reports.domain.TotalSavings;
import es.neifi.myfinance.shared.Infrastructure.containers.ContainersEnvironment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyfinanceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostgresReportRepositoryShould extends ContainersEnvironment {

    @Autowired
    private ReportRepository postgresReportRepository;

    @Autowired
    private ReportRepositortForTest reportRepositortForTest;

    @Test
    void save_new_report_correctly() {
        String reportId = UUID.randomUUID().toString();
        Report report = Report.create(
                new ReportID(reportId),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.from(Instant.now()).getTime()));

        postgresReportRepository.saveReport(report);
        Report expected = reportRepositortForTest.select(reportId);

        assertThat(expected).isEqualTo(report);

    }

    @Test
    void search_existent_report() {
        String reportId = UUID.randomUUID().toString();
        Report report = Report.create(
                new ReportID(reportId),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.from(Instant.now()).getTime()));

        postgresReportRepository.saveReport(report);

        Optional<Report> expected = postgresReportRepository.findById(reportId);

        assertThat(expected).isNotEmpty();
        assertThat(expected).isEqualTo(Optional.of(report));

    }

    @Test
    void dont_return_nonexistent_report() {
        String reportId = UUID.randomUUID().toString();

        Optional<Report> expected = postgresReportRepository.findById(reportId);

        assertThat(expected).isEmpty();
    }

    @Test
    void return_last_report_saved() {
        String lastReportId = UUID.randomUUID().toString();
        Report firstReport = Report.create(
                new ReportID(UUID.randomUUID().toString()),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.from(Instant.now()).getTime()));
        Report secondReport = Report.create(
                new ReportID(UUID.randomUUID().toString()),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.from(Instant.now()).getTime()));

        Report lastReport = Report.create(
                new ReportID(lastReportId),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.from(Instant.now()).getTime()));

        postgresReportRepository.saveReport(firstReport);
        postgresReportRepository.saveReport(secondReport);
        postgresReportRepository.saveReport(lastReport);

        Optional<Report> expected = postgresReportRepository.findLast();

        assertThat(expected).isNotEmpty();
        assertThat(expected).isEqualTo(Optional.of(lastReport));

    }

    @Test
    void do_not_return_last_id_if_no_reports_are_saved_before() {
        Optional<Report> expected = postgresReportRepository.findLast();
        assertThat(expected).isEmpty();
    }
}
