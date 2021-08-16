package es.neifi.myfinance.shared.Infrastructure.configuration;

import es.neifi.myfinance.registry.infrastructure.RegistryRepositoryForTest;
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
    public FlywayMigrationStrategy clean() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }
}
