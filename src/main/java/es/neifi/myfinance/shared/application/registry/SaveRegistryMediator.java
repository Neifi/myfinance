package es.neifi.myfinance.shared.application.registry;

import es.neifi.myfinance.registry.domain.vo.Registry;
import es.neifi.myfinance.reports.application.ReportSaver;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.shared.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;

import static es.neifi.myfinance.registry.application.utils.EventNames.REGISTRY_CREATED;

@Service
public class SaveRegistryMediator implements RegistryMediator {


    private ReportSaver reportSaver;

    public SaveRegistryMediator(ReportSaver reportSaver) {
        this.reportSaver = reportSaver;
    }

    @Override
    public void notifyRegistration(Registry registry, String eventName) {
        if (REGISTRY_CREATED.equals(eventName)){
            reportSaver.save(registry.getDate(),registry.getCost(),registry.isExpense(),registry.getId());
        }
    }
}
