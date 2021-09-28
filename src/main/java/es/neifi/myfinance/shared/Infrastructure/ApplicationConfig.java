package es.neifi.myfinance.shared.Infrastructure;

import es.neifi.myfinance.accountBalance.application.AccountBalanceCreator;
import es.neifi.myfinance.accountBalance.application.AccountBalanceFinder;
import es.neifi.myfinance.accountBalance.application.AccountBalanceUpdater;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.accountBalance.infrastructure.repository.PostgresAccountBalanceRepository;
import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.registry.application.searchRegistry.RegistrySearcher;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.infrastructure.repository.PostgresRegistryRepository;

import es.neifi.myfinance.shared.Infrastructure.bus.event.SpringEventBus;
import es.neifi.myfinance.shared.Infrastructure.cloud.CloudStorageService;
import es.neifi.myfinance.shared.Infrastructure.cloud.aws.S3StorageService;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import es.neifi.myfinance.users.application.find.UserFinder;
import es.neifi.myfinance.users.application.register.UserRegistrator;
import es.neifi.myfinance.users.domain.UserRepository;
import es.neifi.myfinance.users.infrastructure.repository.PostgresUserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    public CloudStorageService cloudStorageService(){
        return new S3StorageService();
    }

    @Bean
    public AccountBalanceCreator accountBalanceCreator(AccountBalanceRepository accountBalanceRepository, UserService userService) {
        return new AccountBalanceCreator(accountBalanceRepository, userService);
    }

    @Bean
    public AccountBalanceRepository accountBalanceRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostgresAccountBalanceRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public AccountBalanceUpdater accountBalanceUpdater(AccountBalanceRepository accountBalanceRepository, UserService userService) {
        return new AccountBalanceUpdater(accountBalanceRepository, userService);
    }

    @Bean
    public AccountBalanceFinder accountBalanceFinder(AccountBalanceRepository accountBalanceRepository, UserService userService) {
        return new AccountBalanceFinder(accountBalanceRepository, userService);
    }


    @Bean
    public UserRepository userRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostgresUserRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public UserFinder userFinder(UserRepository userRepository) {
        return new UserFinder(userRepository);
    }

    @Bean
    public EventBus eventBus(ApplicationEventPublisher applicationEventPublisher) {
        return new SpringEventBus(applicationEventPublisher);
    }


    @Bean
    public RegistrySaver registrySaver(RegistryRepository registryRepository, EventBus eventBus, UserService userService) {
        return new RegistrySaver(registryRepository, eventBus, userService);
    }

    @Bean
    public RegistrySearcher registrySearcher(RegistryRepository registryRepository, UserService userService) {
        return new RegistrySearcher(registryRepository, userService);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }


    @Bean
    public UserRegistrator userRegistrator(UserRepository userRepository, EventBus eventBus) {
        return new UserRegistrator(userRepository, eventBus);
    }

    @Bean
    public RegistryRepository RegistryRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostgresRegistryRepository(namedParameterJdbcTemplate);
    }
}