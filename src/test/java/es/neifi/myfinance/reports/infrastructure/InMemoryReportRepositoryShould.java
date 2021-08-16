package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.reports.domain.*;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryReportRepositoryShould {

    private final ReportRepository inMemoryReportRepository = new InMemoryReportRepository();

    @Test
    void saveReport() throws ParseException {
        Report report = Report.create(
                new ReportID("d26b3d48-beeb-46ca-82cc-5d5b23285447"),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.from(Instant.now()).getTime()));

        inMemoryReportRepository.saveReport(report);

    }

    @Test
    void find_all_reports() throws ParseException {
        Report report = Report.create(
                new ReportID("d26b3d48-beeb-46ca-82cc-5d5b23285447"),
                new TotalExpenses(100),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.from(Instant.now()).getTime()));

        Report report2 = Report.create(
                new ReportID("a12578f5-fdca-4733-9973-9eaa6c9f3ce8"),
                new TotalExpenses(150),
                new TotalIncomes(1000),
                new TotalSavings(900),
                new IsExpense(true),
                new Date(Timestamp.from(Instant.now()).getTime()));

        inMemoryReportRepository.saveReport(report);
        inMemoryReportRepository.saveReport(report2);

        List<Report> reports = inMemoryReportRepository.search();

        assertEquals(2,reports.size());
        assertTrue(reports.contains(report));
        assertTrue(reports.contains(report2));

    }
}