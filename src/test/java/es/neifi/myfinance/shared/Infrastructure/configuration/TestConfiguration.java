package es.neifi.myfinance.shared.Infrastructure.configuration;

import es.neifi.myfinance.registry.infrastructure.RegistryRepositoryForTest;
import es.neifi.myfinance.reports.infrastructure.ReportRepositortForTest;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public RegistryRepositoryForTest registryRepositoryForTest() {
        return new RegistryRepositoryForTest();
    }

    @Bean
    public ReportRepositortForTest reportRepositortForTest(){
        return new ReportRepositortForTest();
    }

    @Bean
    public FlywayMigrationStrategy clean() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }
}