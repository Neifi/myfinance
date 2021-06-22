package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.RegistryCreatedDomainEvent;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.shared.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;

@Service
public class ReportSaver {

    @Autowired
    private ReportRepository reportRepository;

    public ReportSaver(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    public void save(Date date, Cost cost, boolean expense, RegistryID id) {
        Report lastReport = getLastReport();
        Report newReport = Report.builder().build();
        if (lastReport != null) {
            if (expense) {
                newReport = Report.builder()
                        .reportID(new ReportID(id.value()))
                        .date(date)
                        .totalExpenses(lastReport.getTotalExpenses() + cost.getValue())
                        .totalSavings(lastReport.getTotalSavings() - cost.getValue())
                        .totalIncomes(lastReport.getTotalIncomes())
                        .build();
            }
        }
        reportRepository.saveReport(newReport);
    }

    private Report getLastReport() {
        return reportRepository.findLast().orElse(null);
    }

    @EventListener
    public void on(RegistryCreatedDomainEvent event) throws ParseException {
        HashMap<String, Serializable> primitives = event.toPrimitives();
        save(
                new Date((String) primitives.get("date")),
                new Cost(((double) primitives.get("cost"))),
                (boolean) primitives.get("isExpense"),
                new RegistryID((String) primitives.get("registryId")));
    }

}
