package es.neifi.myfinance.registry.infrastructure.integration;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.registry.infrastructure.PostgresRegistryRepository;
import es.neifi.myfinance.shared.Infrastructure.integration.database.DatabaseTestCase;
import es.neifi.myfinance.users.domain.UserID;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostgresRegistryRepositoryShould extends DatabaseTestCase {

    private RegistryRepository registryRepository = new PostgresRegistryRepository();

    void insertRegistryInDb(){
        String id = "9351ea85-88a8-414b-ba57-d21566a05689";

        Registry expectedRegistry = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID(id),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021"));
    }
}