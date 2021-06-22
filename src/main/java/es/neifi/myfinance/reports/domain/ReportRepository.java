package es.neifi.myfinance.reports.domain;

import java.util.Optional;

public interface ReportRepository {
    void saveReport(Report report);

    Optional<Report> findLast();
}
