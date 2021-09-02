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
import es.neifi.myfinance.users.domain.UserID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrySearcherShould {

    private final RegistryRepository registryRepository = Mockito.mock(RegistryRepository.class);
    private final RegistrySearcher registrySearcher = new RegistrySearcher(registryRepository);

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
        assertEquals(id, expense.get().getId().value());
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

        assertEquals(exception.getMessage(),"Registry not found with ID: "+id);
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
        assertEquals(id, expense.get().getId().value());
    }

    @Test
    void search_all_expenses() {
        String userId = "94c8208e-b116-49a8-bf6e-0560135dffb4";
        Registry registry = Registry.createExpense(
                new UserID(userId),
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(Timestamp.from(Instant.now()).getTime()));
        Registry registry2 = Registry.createExpense(
                new UserID(userId),
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(Timestamp.from(Instant.now()).getTime()));
        Registry registry3 = Registry.createExpense(
                new UserID(userId),
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(Timestamp.from(Instant.now()).getTime()));

        List<Registry> expectedExpens = new ArrayList<>();
        expectedExpens.add(registry);
        expectedExpens.add(registry2);
        expectedExpens.add(registry3);

        Mockito.when(registryRepository.searchExpenses(userId)).thenReturn(expectedExpens);

        List<Registry> expens = registrySearcher.findExpenses(userId);

        assertEquals(3, expens.size());
        assertEquals(expectedExpens, expens);
    }

    @Test
    void search_all_expenses_between_dates_in_order() {


        String userId = "94c8208e-b116-49a8-bf6e-0560135dffb4";
        Registry registry = Registry.createExpense(
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
                                1)).getTime()));
        Registry registry2 = Registry.createExpense(
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
                                1)).getTime()));
        Registry registry3 = Registry.createExpense(
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
                                1)).getTime()));
        Registry registry4 = Registry.createExpense(
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
                                1)).getTime()));
        Registry registry5 = Registry.createExpense(
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
                                1)).getTime()));

        List<Registry> expectedExpenses = new ArrayList<>();
        expectedExpenses.add(registry);
        expectedExpenses.add(registry2);
        expectedExpenses.add(registry3);
        expectedExpenses.add(registry4);
        expectedExpenses.add(registry5);

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


        Mockito.when(registryRepository.searchExpenses(userId, intialDate, endDate)).thenReturn(List.of(registry, registry2, registry3));

        List<Registry> expens = registrySearcher.findExpenses(userId, intialDate, endDate);

        assertEquals(3, expens.size());
        assertEquals(expectedExpenses.get(0), expens.get(0));
        assertEquals(expectedExpenses.get(1), expens.get(1));
        assertEquals(expectedExpenses.get(2), expens.get(2));
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
}