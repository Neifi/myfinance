package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;

import java.util.List;
import java.util.Optional;

public class ReportFinder {

    private final ReportRepository reportRepository;

    public ReportFinder(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> findAll(String userId) {
        return reportRepository.search(userId);
    }

    public Optional<Report> findById(String id) {
        return reportRepository.findById(id);
    }
}
