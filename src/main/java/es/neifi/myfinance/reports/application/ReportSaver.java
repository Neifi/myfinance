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
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.domain.UserID;
import org.springframework.context.event.EventListener;

import java.io.Serializable;
import java.util.HashMap;

public class ReportSaver {

    private final ReportRepository reportRepository;
    private final ReportCalculator reportCalculator;
    private final ReportFinder reportFinder;
    private final UserService userService;

    public ReportSaver(ReportRepository reportRepository, UserService userService) {
        this.userService = userService;
        this.reportRepository = reportRepository;
        this.reportFinder = new ReportFinder(reportRepository, userService);
        this.reportCalculator = new ReportCalculator(reportRepository);
    }

    @EventListener
    public void on(RegistryCreatedDomainEvent event) {
        Registry registry;

        if (event.isExpense()) {
            registry = createExpense(event);

            Report calculatedReport = reportCalculator.calculate(registry);
            saveReport(calculatedReport);
        } else {
            registry = createIncome(event);

            Report calculatedReport = reportCalculator.calculate(registry);
            saveReport(calculatedReport);
        }

    }

    private Registry createIncome(RegistryCreatedDomainEvent event) {
        return Registry.createIncome(
                new UserID(event.userId()),
                new RegistryID(event.getAggregateId()),
                new Category(event.category()),
                new Name(event.name()),
                new Cost(event.cost()),
                new Currency(event.currency()),
                new Date(event.date())
        );
    }

    private Registry createExpense(RegistryCreatedDomainEvent event) {
        return Registry.createExpense(
                new UserID(event.userId()),
                new RegistryID(event.getAggregateId()),
                new Category(event.category()),
                new Name(event.name()),
                new Cost(event.cost()),
                new Currency(event.currency()),
                new Date(event.date())
        );
    }

    private Registry deserializeExpenseRegistry(HashMap<String, Serializable> primitives) {
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

    private Registry deserializeIncomeRegistry(HashMap<String, Serializable> primitives) {
        return Registry.createIncome(
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
        String searchedReport = report.getReportId().value();
        String userId = report.getUserId().value();

        if (reportFinder.findById(userId, searchedReport).isEmpty()) {
            reportRepository.saveReport(report);
        }
    }
}