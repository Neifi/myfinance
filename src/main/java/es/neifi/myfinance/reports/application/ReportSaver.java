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

    private ReportRepository reportRepository;

    public ReportSaver(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    public void save(Report report) {
        Report lastReport = getLastReport();
        Report newReport = Report.builder().build();
        if (lastReport != null) {
            if (report.isExpense()) {
                newReport = Report.builder()
                        .reportID(new ReportID(report.getReportID().value()))
                        .date(report.getDate())
                        .totalExpenses(0)
                        .totalSavings(0)
                        .totalIncomes(0)
                        .build();
            }
        }
        reportRepository.saveReport(newReport);
    }

    private Report getLastReport() {
        return reportRepository.findLast().orElse(null);
    }

    @EventListener
    private void on(RegistryCreatedDomainEvent event) throws ParseException {
        HashMap<String, Serializable> primitives = event.toPrimitives();
        save(Report.builder().build());
    }

}
