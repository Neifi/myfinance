package es.neifi.myfinance.reports.domain;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {

    List<Report> search();
    void saveReport(Report report);
    Optional<Report> findLast();

    Optional<Report> findById(String id);
}
