package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.shared.domain.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportFinder {

    private final ReportRepository reportRepository;

    public ReportFinder(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> findAll() {
        return reportRepository.search();
    }

    public Optional<Report> findById(String id) {
        return reportRepository.findById(id);
    }
}
