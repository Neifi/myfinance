package es.neifi.myfinance.registry.application.searchExpense;

import es.neifi.myfinance.registry.application.searchRegistry.RegistrySearcher;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RegistrySearcherShould {

    private final RegistryRepository registryRepository = Mockito.mock(RegistryRepository.class);
    private RegistrySearcher registrySearcher = new RegistrySearcher(registryRepository);

    @Test
    void search_one_expense() throws ParseException {
        String id = "22aa0d3b-07eb-4f19-8320-1b3c3a25b070";

        Registry expectedRegistry = Registry.createExpense(
                new RegistryID(id),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021"));


        Mockito.when(registryRepository.searchExpenseById(id)).thenReturn(Optional.of(expectedRegistry));
        Optional<Registry> expense = registrySearcher.searchExpense(id);

        assertTrue(expense.isPresent());
        assertTrue(expense.get().isExpense());
        assertEquals(id,expense.get().getId().value());
    }
    @Test
    void search_one_income() throws ParseException {
        String id = "22aa0d3b-07eb-4f19-8320-1b3c3a25b070";

        Registry expectedRegistry = Registry.createExpense(
                new RegistryID(id),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021"));


        Mockito.when(registryRepository.searchIncomeById(id)).thenReturn(Optional.of(expectedRegistry));
        Optional<Registry> expense = registrySearcher.searchIncome(id);

        assertTrue(expense.isPresent());
        assertTrue(expense.get().isExpense());
        assertEquals(id,expense.get().getId().value());
    }

    @Test
    void search_all_expenses() throws ParseException {
        Registry registry = Registry.createExpense(
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021"));
        Registry registry2 = Registry.createExpense(
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021"));
        Registry registry3 = Registry.createExpense(
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021"));

        List<Registry> expectedExpens = new ArrayList<>();
        expectedExpens.add(registry);
        expectedExpens.add(registry2);
        expectedExpens.add(registry3);

        Mockito.when(registryRepository.searchExpenses()).thenReturn(expectedExpens);

        List<Registry> expens = registrySearcher.searchExpenses();

        assertEquals(3, expens.size());
        assertEquals(expectedExpens, expens);
    }

    @Test
    void search_all_expenses_between_dates_in_order() throws ParseException {
        Registry registry = Registry.createExpense(
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021"));
        Registry registry2 = Registry.createExpense(
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("07/06/2021"));
        Registry registry3 = Registry.createExpense(
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("06/06/2021"));
        Registry registry4 = Registry.createExpense(
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("06/05/2021"));
        Registry registry5 = Registry.createExpense(
                new RegistryID("7c4de261-a6f1-4ce7-acc3-7c05e3f9d035"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("28/12/2021"));

        List<Registry> expectedExpens = new ArrayList<>();
        expectedExpens.add(registry);
        expectedExpens.add(registry2);
        expectedExpens.add(registry3);
        expectedExpens.add(registry4);
        expectedExpens.add(registry5);

        String intialDate = "01/06/2021";
        String endDate = "30/06/2021";
        Mockito.when(registryRepository.searchExpenseInRange(intialDate,endDate)).thenReturn(List.of(registry,registry2,registry3));

        List<Registry> expens = registrySearcher.searchExpenses(intialDate, endDate);

        assertEquals(3, expens.size());
        assertEquals(expectedExpens.get(0), expens.get(0));
        assertEquals(expectedExpens.get(1), expens.get(1));
        assertEquals(expectedExpens.get(2), expens.get(2));
    }

    @Test
    void return_empty_list_when_no_expenses_between_dates() {
        List<Registry> expens = registrySearcher.searchIncome("01/06/2021","30/06/2021");
        assertTrue(expens.isEmpty());
    }
}