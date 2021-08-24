package es.neifi.myfinance.reports.domain;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {

    List<Report> search(String userId);

    void saveReport(Report report);

    Optional<Report> findLast(String userId);

    Optional<Report> findById(String id);
}
