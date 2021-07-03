package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.shared.domain.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
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
        reports.put(report.getReportID().value(),report);
        lastSavedId = report.getReportID().value();
    }

    @Override
    public Optional<Report> findLast() {
        return Optional.ofNullable(reports.get(lastSavedId));
    }
}
