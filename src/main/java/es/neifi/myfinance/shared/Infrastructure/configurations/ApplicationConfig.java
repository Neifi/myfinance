package es.neifi.myfinance.shared.Infrastructure.configurations;

import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.registry.infrastructure.InMemoryRegistryRepository;
import es.neifi.myfinance.reports.application.ReportFinder;
import es.neifi.myfinance.reports.infrastructure.InMemoryReportRepository;
import es.neifi.myfinance.shared.Infrastructure.bus.event.SpringEventBus;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ApplicationConfig {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public ApplicationConfig() {
    }

    @Bean
    public FireStoreConfiguration fireStoreConfiguration() throws IOException {
        return new FireStoreConfiguration();
    }


    @Bean
    public EventBus eventBus() {
        return new SpringEventBus(applicationEventPublisher);
    }


    @Bean
    public ReportFinder reportFinder(){
        return new ReportFinder(new InMemoryReportRepository());
    }

    @Bean
    public RegistrySaver registrySaver(){
        return new RegistrySaver(new InMemoryRegistryRepository(),eventBus());
    }
}
