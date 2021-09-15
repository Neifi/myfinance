package es.neifi.myfinance.registry.application.searchExpense;

import es.neifi.myfinance.registry.application.exceptions.RegistryNotFoundException;
import es.neifi.myfinance.registry.application.searchRegistry.RegistrySearcher;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.domain.UserID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrySearcherShould {

    private final RegistryRepository registryRepository = Mockito.mock(RegistryRepository.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final RegistrySearcher registrySearcher = new RegistrySearcher(registryRepository, userService);

    @Test
    void search_one_expense() {
        String id = "22aa0d3b-07eb-4f19-8320-1b3c3a25b070";

        Registry expectedRegistry = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID(id),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(Timestamp.from(Instant.now()).getTime()));


        Mockito.when(registryRepository.searchRegistryById(id)).thenReturn(Optional.of(expectedRegistry));
        Optional<Registry> expense = registrySearcher.findRegistry(id);

        assertTrue(expense.isPresent());
        assertTrue(expense.get().isExpense());
        assertEquals(id, expense.get().id().value());
    }

    @Test
    void throw_exception_when_registry_not_found() {
        String id = "22aa0d3b-07eb-4f19-8320-1b3c3a25b070";

        Registry expectedRegistry = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID(id),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(Timestamp.from(Instant.now()).getTime()));


        Mockito.when(registryRepository.searchRegistryById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RegistryNotFoundException.class, () -> {
            Optional<Registry> expense = registrySearcher.findRegistry(id);
        });

        assertEquals(exception.getMessage(), "Registry not found with ID: " + id);
    }

    @Test
    void search_one_income() {
        String id = "22aa0d3b-07eb-4f19-8320-1b3c3a25b070";

        Registry expectedRegistry = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID(id),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(Timestamp.from(Instant.now()).getTime()));


        Mockito.when(registryRepository.searchRegistryById(id)).thenReturn(Optional.of(expectedRegistry));
        Optional<Registry> expense = registrySearcher.findRegistry(id);

        assertTrue(expense.isPresent());
        assertTrue(expense.get().isExpense());
        assertEquals(id, expense.get().id().value());
    }

    @Test
    void search_all_expenses() {
        String userId = "94c8208e-b116-49a8-bf6e-0560135dffb4";
        List<Registry> expectedExpenses = createExpenses(userId);

        Mockito.when(registryRepository.searchExpenses(userId)).thenReturn(expectedExpenses);

        List<Registry> expens = registrySearcher.findExpenses(userId);

        assertEquals(5, expens.size());
        assertEquals(expectedExpenses, expens);
    }

    @Test
    void search_all_incomes() {
        String userId = "94c8208e-b116-49a8-bf6e-0560135dffb4";
        List<Registry> expectedIncomes = createIncomes(userId);

        Mockito.when(registryRepository.searchIncomes(userId)).thenReturn(expectedIncomes);

        List<Registry> incomes = registrySearcher.findIncomes(userId);

        assertEquals(5, incomes.size());
        assertEquals(expectedIncomes, incomes);
    }


    @Test
    void search_all_expenses_between_dates_in_order() {

        String userId = "94c8208e-b116-49a8-bf6e-0560135dffb4";
        List<Registry> expectedExpenses = createExpenses(userId);
        Long intialDate = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                8,
                15,
                1)).getTime();
        Long endDate = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime();


        Mockito.when(registryRepository.searchExpenses(userId, intialDate, endDate)).thenReturn(List.of(expectedExpenses.get(0),expectedExpenses.get(1),expectedExpenses.get(2)));

        List<Registry> expens = registrySearcher.findExpenses(userId, intialDate, endDate);

        assertEquals(3, expens.size());
        assertEquals(expectedExpenses.get(0), expens.get(0));
        assertEquals(expectedExpenses.get(1), expens.get(1));
        assertEquals(expectedExpenses.get(2), expens.get(2));
    }

    @Test
    void search_all_incomes_between_dates_in_order() {

        String userId = "94c8208e-b116-49a8-bf6e-0560135dffb4";

        List<Registry> expectedIncomes = createIncomes(userId);

        Long intialDate = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                8,
                15,
                1)).getTime();
        Long endDate = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime();


        Mockito.when(registryRepository.searchIncomes(userId, intialDate, endDate)).thenReturn(List.of(expectedIncomes.get(0), expectedIncomes.get(1), expectedIncomes.get(2)));

        List<Registry> actualIncomes = registrySearcher.findIncomes(userId, intialDate, endDate);

        assertEquals(3, actualIncomes.size());
        assertEquals(expectedIncomes.get(0), actualIncomes.get(0));
        assertEquals(expectedIncomes.get(1), actualIncomes.get(1));
        assertEquals(expectedIncomes.get(2), actualIncomes.get(2));
    }


    @Test
    void return_empty_list_when_no_expenses_between_dates() {
        String userId = UUID.randomUUID().toString();
        Long intialDate = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                8,
                15,
                1)).getTime();
        Long endDate = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime();
        List<Registry> expens = registrySearcher.findRegistry(userId, intialDate, endDate);
        assertTrue(expens.isEmpty());
    }

    private List<Registry> createIncomes(String userId) {
        return List.of(Registry.createIncome(
                        new UserID(userId),
                        new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                        new Category("home"),
                        new Name("internet"),
                        new Cost(100),
                        new Currency("EUR"),
                        new Date(Timestamp.valueOf(LocalDateTime
                                .of(
                                        2021,
                                        6,
                                        8,
                                        15,
                                        1)).getTime())),
                Registry.createIncome(
                        new UserID(userId),
                        new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                        new Category("home"),
                        new Name("internet"),
                        new Cost(100),
                        new Currency("EUR"),
                        new Date(Timestamp.valueOf(LocalDateTime
                                .of(
                                        2021,
                                        6,
                                        7,
                                        15,
                                        1)).getTime())),
                Registry.createIncome(
                        new UserID(userId),
                        new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                        new Category("home"),
                        new Name("internet"),
                        new Cost(100),
                        new Currency("EUR"),
                        new Date(Timestamp.valueOf(LocalDateTime
                                .of(
                                        2021,
                                        6,
                                        6,
                                        15,
                                        1)).getTime())),
                Registry.createIncome(
                        new UserID(userId),
                        new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                        new Category("home"),
                        new Name("internet"),
                        new Cost(100),
                        new Currency("EUR"),
                        new Date(Timestamp.valueOf(LocalDateTime
                                .of(
                                        2021,
                                        6,
                                        5,
                                        15,
                                        1)).getTime())),
                Registry.createIncome(
                        new UserID(userId),
                        new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                        new Category("home"),
                        new Name("internet"),
                        new Cost(100),
                        new Currency("EUR"),
                        new Date(Timestamp.valueOf(LocalDateTime
                                .of(
                                        2021,
                                        12,
                                        28,
                                        15,
                                        1)).getTime())));

    }

    private List<Registry> createExpenses(String userId) {
        return List.of(Registry.createExpense(
                        new UserID(userId),
                        new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                        new Category("home"),
                        new Name("internet"),
                        new Cost(100),
                        new Currency("EUR"),
                        new Date(Timestamp.valueOf(LocalDateTime
                                .of(
                                        2021,
                                        6,
                                        8,
                                        15,
                                        1)).getTime())),
                Registry.createExpense(
                        new UserID(userId),
                        new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                        new Category("home"),
                        new Name("internet"),
                        new Cost(100),
                        new Currency("EUR"),
                        new Date(Timestamp.valueOf(LocalDateTime
                                .of(
                                        2021,
                                        6,
                                        7,
                                        15,
                                        1)).getTime())),
                Registry.createExpense(
                        new UserID(userId),
                        new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                        new Category("home"),
                        new Name("internet"),
                        new Cost(100),
                        new Currency("EUR"),
                        new Date(Timestamp.valueOf(LocalDateTime
                                .of(
                                        2021,
                                        6,
                                        6,
                                        15,
                                        1)).getTime())),
                Registry.createExpense(
                        new UserID(userId),
                        new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                        new Category("home"),
                        new Name("internet"),
                        new Cost(100),
                        new Currency("EUR"),
                        new Date(Timestamp.valueOf(LocalDateTime
                                .of(
                                        2021,
                                        6,
                                        5,
                                        15,
                                        1)).getTime())),
                Registry.createExpense(
                        new UserID(userId),
                        new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                        new Category("home"),
                        new Name("internet"),
                        new Cost(100),
                        new Currency("EUR"),
                        new Date(Timestamp.valueOf(LocalDateTime
                                .of(
                                        2021,
                                        12,
                                        28,
                                        15,
                                        1)).getTime())));

    }
}