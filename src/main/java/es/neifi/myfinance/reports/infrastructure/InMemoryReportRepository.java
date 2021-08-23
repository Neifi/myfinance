package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;


public class InMemoryReportRepository implements ReportRepository {

    private HashMap<String, Report> reports = new LinkedHashMap<>();
    private String lastSavedId;

    @Override
    public Optional<Report>findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Report> search() {
        return new ArrayList<>(reports.values());
    }

    @Override
    public void saveReport(Report report) {
        reports.put(report.getReportId().value(),report);
        lastSavedId = report.getReportId().value();
    }

    @Override
    public Optional<Report> findLast() {
        return Optional.ofNullable(reports.get(lastSavedId));
    }
}
