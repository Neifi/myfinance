package es.neifi.myfinance.shared.Infrastructure.configuration;

import es.neifi.myfinance.registry.infrastructure.RegistryRepositoryForTest;
import es.neifi.myfinance.users.infrastructure.repository.UserRepositoryForTest;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TestConfiguration {

    @Bean
    public RegistryRepositoryForTest registryRepositoryForTest() {
        return new RegistryRepositoryForTest();
    }


    @Bean
    public UserRepositoryForTest userRepositoryForTest() {
        return new UserRepositoryForTest();
    }

    @Bean
    @Profile("test")
    public FlywayMigrationStrategy cleanMigrateStrategy() {

        return flyway -> {
            flyway.clean();
            flyway.migrate();
            flyway.repair();
        };
    }
}
