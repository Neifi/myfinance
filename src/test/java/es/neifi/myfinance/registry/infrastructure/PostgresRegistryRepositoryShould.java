package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.MyfinanceApplication;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.shared.Infrastructure.containers.ContainersEnvironment;
import es.neifi.myfinance.users.domain.UserID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static es.neifi.myfinance.shared.utils.DateUtils.timestampOf;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyfinanceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostgresRegistryRepositoryShould extends ContainersEnvironment {

    @Autowired
    private RegistryRepository registryRepository;

    @Autowired
    private RegistryRepositoryForTest registryRepositoryForTest;

    @Autowired
    FlywayMigrationStrategy flywayMigrationStrategy;

    @Test
    public void save_registry_in_db() {

        String registryId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        Long date = timestampOf(2021, 11, 27, 10, 30);
        String category = "some-cat";
        String name = "some-name";
        double cost = 10.00;
        String currency = "EUR";

        Registry registry = Registry.createExpense(
                new UserID(userId),
                new RegistryID(registryId),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );


        registryRepository.save(registry);
        assertThat(registryRepositoryForTest.select(registryId))
                .isEqualTo(registry);

    }

    @Test
    void find_registry_by_id() {
        String registryId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        Long date = timestampOf(2021, 11, 27, 10, 30);
        String category = "some-cat";
        String name = "some-name";
        double cost = 10.00;
        String currency = "EUR";

        Registry registry = Registry.createExpense(
                new UserID(userId),
                new RegistryID(registryId),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );


        registryRepository.save(registry);
        Optional<Registry> expectedRegistry = registryRepository.searchRegistryById(registryId);

        assertThat(expectedRegistry).isNotEmpty();
        assertThat(expectedRegistry.get()).isEqualTo(registry);
    }

    @Test
    void not_return_nonexistent_user() {
        Optional<Registry> expectedRegistry = registryRepository.searchRegistryById("SUPER_ID");

        assertThat(expectedRegistry).isEmpty();

    }

    @Test
    void return_list_of_registries_from_a_given_userId() {
        String registryId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        Long date = timestampOf(2021, 11, 27, 10, 30);
        String category = "some-cat";
        String name = "some-name";
        double cost = 10.00;
        String currency = "EUR";

        Registry registry = Registry.createExpense(
                new UserID(userId),
                new RegistryID(registryId),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        Registry registry2 = Registry.createExpense(
                new UserID(userId),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        Registry registry3 = Registry.createExpense(
                new UserID(UUID.randomUUID().toString()),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        registryRepository.save(registry);
        registryRepository.save(registry2);
        registryRepository.save(registry3);

        List<Registry> expectedRegistries = registryRepository.search(userId);

        assertThat(expectedRegistries).isNotEmpty();
        assertThat(expectedRegistries.size()).isEqualTo(2);
        assertThat(expectedRegistries.get(0)).isEqualTo(registry);
        assertThat(expectedRegistries.get(1)).isEqualTo(registry2);
    }

    @Test
    void return_empty_list_if_user_dont_have_registries() {
        String userId = UUID.randomUUID().toString();
        List<Registry> expectedRegistries = registryRepository.search(userId);

        assertThat(expectedRegistries).isEmpty();
    }

    @Test
    void return_list_of_incomes_from_a_given_userId() {
        String registryId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        Long date = timestampOf(2021, 11, 27, 10, 30);
        String category = "some-cat";
        String name = "some-name";
        double cost = 10.00;
        String currency = "EUR";

        Registry registry = Registry.createIncome(
                new UserID(userId),
                new RegistryID(registryId),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        Registry registry2 = Registry.createIncome(
                new UserID(userId),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        Registry registry3 = Registry.createExpense(
                new UserID(UUID.randomUUID().toString()),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        registryRepository.save(registry);
        registryRepository.save(registry2);
        registryRepository.save(registry3);

        List<Registry> expectedRegistries = registryRepository.searchIncomes(userId);

        assertThat(expectedRegistries).isNotEmpty();
        assertThat(expectedRegistries.size()).isEqualTo(2);
        assertThat(expectedRegistries.get(0)).isEqualTo(registry);
        assertThat(expectedRegistries.get(1)).isEqualTo(registry2);
    }

    @Test
    void return_empty_list_if_user_dont_have_incomes() {
        String userId = UUID.randomUUID().toString();
        List<Registry> expectedRegistries = registryRepository.searchIncomes(userId);

        assertThat(expectedRegistries).isEmpty();
    }

    @Test
    void return_list_of_expenses_from_a_given_userId() {
        String registryId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        Long date = timestampOf(2021, 11, 27, 10, 30);
        String category = "some-cat";
        String name = "some-name";
        double cost = 10.00;
        String currency = "EUR";

        Registry registry = Registry.createExpense(
                new UserID(userId),
                new RegistryID(registryId),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        Registry registry2 = Registry.createExpense(
                new UserID(userId),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        Registry registry3 = Registry.createExpense(
                new UserID(UUID.randomUUID().toString()),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        registryRepository.save(registry);
        registryRepository.save(registry2);
        registryRepository.save(registry3);

        List<Registry> expectedRegistries = registryRepository.searchExpenses(userId);

        assertThat(expectedRegistries).isNotEmpty();
        assertThat(expectedRegistries.size()).isEqualTo(2);
        assertThat(expectedRegistries.get(0)).isEqualTo(registry);
        assertThat(expectedRegistries.get(1)).isEqualTo(registry2);
    }

    @Test
    void return_empty_list_if_user_dont_have_expenses() {
        String userId = UUID.randomUUID().toString();
        List<Registry> expectedRegistries = registryRepository.searchExpenses(userId);

        assertThat(expectedRegistries).isEmpty();
    }

    @Test
    void search_expenses_in_date_range() {
        String registryId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        Long date = timestampOf(2021, 11, 30, 10, 30);
        Long date1 = timestampOf(2021, 11, 1, 10, 30);
        Long date2 = timestampOf(2021, 10, 31, 10, 30);
        Long date3 = timestampOf(2021, 10, 1, 10, 30);
        String category = "some-cat";
        String name = "some-name";
        double cost = 10.00;
        String currency = "EUR";

        Registry registry = Registry.createExpense(
                new UserID(userId),
                new RegistryID(registryId),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        Registry registry1 = Registry.createExpense(
                new UserID(userId),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date1)
        );

        Registry registry2 = Registry.createExpense(
                new UserID(UUID.randomUUID().toString()),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date2)
        );

        Registry registry3 = Registry.createExpense(
                new UserID(UUID.randomUUID().toString()),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date3)
        );

        registryRepository.save(registry);
        registryRepository.save(registry1);
        registryRepository.save(registry2);
        registryRepository.save(registry3);

        List<Registry> expectedRegistries = registryRepository.searchExpenses(userId, date, date1);

        assertThat(expectedRegistries.size()).isEqualTo(2);
        assertThat(expectedRegistries.get(0)).isEqualTo(registry);
        assertThat(expectedRegistries.get(1)).isEqualTo(registry1);
    }

    @Test
    void return_empty_list_when_no_expenses_in_date_range() {
        String registryId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        Long date = timestampOf(2021, 11, 30, 10, 30);
        Long date1 = timestampOf(2021, 11, 1, 10, 30);
        Long date2 = timestampOf(2021, 10, 31, 10, 30);
        Long date3 = timestampOf(2021, 10, 1, 10, 30);
        String category = "some-cat";
        String name = "some-name";
        double cost = 10.00;
        String currency = "EUR";

        Registry registry = Registry.createExpense(
                new UserID(userId),
                new RegistryID(registryId),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        Registry registry1 = Registry.createExpense(
                new UserID(userId),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date1)
        );


        registryRepository.save(registry);
        registryRepository.save(registry1);

        List<Registry> expectedRegistries = registryRepository.searchExpenses(userId, date2, date3);

        assertThat(expectedRegistries).isEmpty();
    }

    @Test
    void search_incomes_in_date_range() {
        String registryId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        Long date = timestampOf(2021, 11, 30, 10, 30);
        Long date1 = timestampOf(2021, 11, 1, 10, 30);
        Long date2 = timestampOf(2021, 10, 31, 10, 30);
        Long date3 = timestampOf(2021, 10, 1, 10, 30);
        String category = "some-cat";
        String name = "some-name";
        double cost = 10.00;
        String currency = "EUR";

        Registry registry = Registry.createIncome(
                new UserID(userId),
                new RegistryID(registryId),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        Registry registry1 = Registry.createIncome(
                new UserID(userId),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date1)
        );

        Registry registry2 = Registry.createIncome(
                new UserID(UUID.randomUUID().toString()),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date2)
        );

        Registry registry3 = Registry.createIncome(
                new UserID(UUID.randomUUID().toString()),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date3)
        );

        registryRepository.save(registry);
        registryRepository.save(registry1);
        registryRepository.save(registry2);
        registryRepository.save(registry3);

        List<Registry> expectedRegistries = registryRepository.searchIncomes(userId, date, date1);

        assertThat(expectedRegistries.size()).isEqualTo(2);
        assertThat(expectedRegistries.get(0)).isEqualTo(registry);
        assertThat(expectedRegistries.get(1)).isEqualTo(registry1);
    }

    @Test
    void return_empty_list_when_no_incomes_in_date_range() {
        String registryId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        Long date = timestampOf(2021, 11, 30, 10, 30);
        Long date1 = timestampOf(2021, 11, 1, 10, 30);
        Long date2 = timestampOf(2021, 10, 31, 10, 30);
        Long date3 = timestampOf(2021, 10, 1, 10, 30);
        String category = "some-cat";
        String name = "some-name";
        double cost = 10.00;
        String currency = "EUR";

        Registry registry = Registry.createIncome(
                new UserID(userId),
                new RegistryID(registryId),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date)
        );

        Registry registry1 = Registry.createIncome(
                new UserID(userId),
                new RegistryID(UUID.randomUUID().toString()),
                new Category(category),
                new Name(name),
                new Cost(cost),
                new Currency(currency),
                new Date(date1)
        );


        registryRepository.save(registry);
        registryRepository.save(registry1);

        List<Registry> expectedRegistries = registryRepository.searchIncomes(userId, date2, date3);

        assertThat(expectedRegistries).isEmpty();
    }
}