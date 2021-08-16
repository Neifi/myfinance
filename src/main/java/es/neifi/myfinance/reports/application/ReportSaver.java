package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryCreatedDomainEvent;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.users.domain.UserID;
import org.springframework.context.event.EventListener;

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;

public class ReportSaver {

    private final ReportRepository reportRepository;
    private final ReportCalculator reportCalculator;
    private final ReportFinder reportFinder;

    public ReportSaver(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
        this.reportFinder = new ReportFinder(reportRepository);
        this.reportCalculator = new ReportCalculator(reportRepository);
    }

    @EventListener
    public void on(RegistryCreatedDomainEvent event) throws ParseException {
        HashMap<String, Serializable> primitives = event.toPrimitives();
        Report report = reportCalculator.calculate(deserializeRegistry(primitives));
        saveReport(report);

    }

    private Registry deserializeRegistry(HashMap<String, Serializable> primitives) {
        return Registry.createExpense(
                new UserID((String) primitives.get("userId")),
                new RegistryID((String) primitives.get("registryId")),
                new Category((String) primitives.get("category")),
                new Name((String) primitives.get("name")),
                new Cost((double) primitives.get("cost")),
                new Currency((String) primitives.get("currency")),
                new Date((Long) primitives.get("date"))
        );
    }
    
    public void saveReport(Report report) {
        if (reportFinder.findById(report.getReportID().value()).isEmpty()) {
            reportRepository.saveReport(report);
        }
    }
}