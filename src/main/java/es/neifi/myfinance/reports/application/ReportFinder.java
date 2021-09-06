package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.shared.domain.UserService;

import java.util.List;
import java.util.Optional;

public class ReportFinder {

    private final ReportRepository reportRepository;
    private final UserService userService;

    public ReportFinder(ReportRepository reportRepository, UserService userService) {
        this.reportRepository = reportRepository;
        this.userService = userService;
    }

    public List<Report> findAll(String userId){
        userService.find(userId);
        return reportRepository.search(userId);
    }

    public Optional<Report> findById(String userId,String reportId) {
        userService.find(userId);
        return reportRepository.findById(reportId);
    }
}
