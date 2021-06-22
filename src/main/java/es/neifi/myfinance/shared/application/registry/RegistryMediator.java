package es.neifi.myfinance.shared.application.registry;

import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.registry.domain.vo.Registry;
import es.neifi.myfinance.reports.application.ReportSaver;

public interface RegistryMediator {

    void notifyRegistration(Registry registry, String eventName);

}
