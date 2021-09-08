package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;

import java.util.Optional;

public class ReportService {

    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Optional<Report> findReport(String reportId) {
        return reportRepository.findById(reportId);
    }
}
