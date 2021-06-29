package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.shared.domain.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class InMemoryReportRepository implements ReportRepository {

    private HashMap<String, Report> reports = new LinkedHashMap<>();
    private String lastSavedId;
    @Override
    public void saveReport(Report report) {
        reports.put(report.getReportID().value(),report);
        lastSavedId = report.getReportID().value();
    }

    @Override
    public Optional<Report> findLast() {
        return Optional.ofNullable(reports.get(lastSavedId));
    }
}
