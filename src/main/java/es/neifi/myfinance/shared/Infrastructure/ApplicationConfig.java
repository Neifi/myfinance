package es.neifi.myfinance.shared.Infrastructure;

import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.registry.application.searchRegistry.RegistrySearcher;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.infrastructure.PostgresRegistryRepository;
import es.neifi.myfinance.reports.application.ReportCalculator;
import es.neifi.myfinance.reports.application.ReportFinder;
import es.neifi.myfinance.reports.application.ReportSaver;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.reports.infrastructure.PostgresReportRepository;
import es.neifi.myfinance.shared.Infrastructure.bus.event.SpringEventBus;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import es.neifi.myfinance.users.application.register.UserRegistrator;
import es.neifi.myfinance.users.domain.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    @Bean
    public EventBus eventBus(ApplicationEventPublisher applicationEventPublisher) {
        return new SpringEventBus(applicationEventPublisher);
    }

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public ReportRepository reportRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostgresReportRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public ReportFinder reportFinder(ReportRepository reportRepository) {
        return new ReportFinder(reportRepository);
    }

    @Bean
    public RegistrySaver registrySaver(RegistryRepository registryRepository, EventBus eventBus) {
        return new RegistrySaver(registryRepository, eventBus);
    }

    @Bean
    public RegistrySearcher registrySearcher(RegistryRepository registryRepository) {
        return new RegistrySearcher(registryRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public UserRegistrator userRegistrator(UserRepository userRepository) {
        return new UserRegistrator(userRepository);
    }

    @Bean
    public RegistryRepository RegistryRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostgresRegistryRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public ReportCalculator reportCalculator(ReportRepository reportRepository) {
        return new ReportCalculator(reportRepository);
    }

    @Bean
    public ReportSaver reportSaver(ReportRepository reportRepository) {
        return new ReportSaver(reportRepository);
    }
}