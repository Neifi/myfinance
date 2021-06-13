package es.neifi.myfinance.registries.application.searchExpense;

import es.neifi.myfinance.registry.application.searchRegistry.ExpenseSearcher;
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
    private ExpenseSearcher expenseSearcher = new ExpenseSearcher(registryRepository);

    @Test
    void search_one_expense() throws ParseException {
        String id = "22aa0d3b-07eb-4f19-8320-1b3c3a25b070";
        Registry expectedRegistry = Registry.builder()
                .id(new RegistryID(id))
                .category(new Category("home"))
                .name(new Name("internet"))
                .cost(new Cost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Mockito.when(registryRepository.search(id)).thenReturn(Optional.ofNullable(expectedRegistry));
        Optional<Registry> expense = expenseSearcher.search(id);

        assertTrue(expense.isPresent());
        assertEquals(id,expense.get().getId().value());
    }

    @Test
    void search_all_expenses() throws ParseException {
        Registry registry = Registry.builder()
                .id(new RegistryID("787f28f2-003a-4445-8659-d60683107845"))
                .category(new Category("home"))
                .name(new Name("internet"))
                .cost(new Cost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Registry registry2 = Registry.builder()
                .id(new RegistryID("cb99dff2-9f0a-4bb7-88a8-4b3b5937e6c5"))
                .category(new Category("home"))
                .name(new Name("invoice"))
                .cost(new Cost(110))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Registry registry3 = Registry.builder()
                .id(new RegistryID("f3b97578-4c18-4753-a4d5-364f0099423b"))
                .category(new Category("home"))
                .name(new Name("invoice2"))
                .cost(new Cost(50))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();

        List<Registry> expectedExpens = new ArrayList<>();
        expectedExpens.add(registry);
        expectedExpens.add(registry2);
        expectedExpens.add(registry3);

        Mockito.when(registryRepository.search()).thenReturn(expectedExpens);

        List<Registry> expens = expenseSearcher.search();

        assertEquals(3, expens.size());
        assertEquals(expectedExpens, expens);
    }

    @Test
    void search_all_expenses_between_dates_in_order() throws ParseException {
        Registry registry = Registry.builder()
                .id(new RegistryID("787f28f2-003a-4445-8659-d60683107845"))
                .category(new Category("home"))
                .name(new Name("internet"))
                .cost(new Cost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Registry registry2 = Registry.builder()
                .id(new RegistryID("cb99dff2-9f0a-4bb7-88a8-4b3b5937e6c5"))
                .category(new Category("home"))
                .name(new Name("invoice"))
                .cost(new Cost(110))
                .currency(new Currency("EUR"))
                .date(new Date("07/06/2021"))
                .build();
        Registry registry3 = Registry.builder()
                .id(new RegistryID("f3b97578-4c18-4753-a4d5-364f0099423b"))
                .category(new Category("home"))
                .name(new Name("invoice2"))
                .cost(new Cost(50))
                .currency(new Currency("EUR"))
                .date(new Date("06/06/2021"))
                .build();
        Registry registry4 = Registry.builder()
                .id(new RegistryID("80d729f2-be4a-4228-a9ef-3c140357abe1"))
                .category(new Category("home"))
                .name(new Name("invoice3"))
                .cost(new Cost(50))
                .currency(new Currency("EUR"))
                .date(new Date("08/05/2021"))
                .build();
        Registry registry5 = Registry.builder()
                .id(new RegistryID("9f0c6936-67b7-4793-ad08-c3c3197952b6"))
                .category(new Category("home"))
                .name(new Name("invoice4"))
                .cost(new Cost(50))
                .currency(new Currency("EUR"))
                .date(new Date("08/05/2020"))
                .build();
        List<Registry> expectedExpens = new ArrayList<>();
        expectedExpens.add(registry);
        expectedExpens.add(registry2);
        expectedExpens.add(registry3);
        expectedExpens.add(registry4);
        expectedExpens.add(registry5);

        Mockito.when(registryRepository.search()).thenReturn(expectedExpens);

        List<Registry> expens = expenseSearcher.search("01/06/2021","30/06/2021");

        assertEquals(3, expens.size());
        assertEquals(expectedExpens.get(0), expens.get(0));
        assertEquals(expectedExpens.get(1), expens.get(1));
        assertEquals(expectedExpens.get(2), expens.get(2));
    }

    @Test
    void return_empty_list_when_no_expenses_between_dates() {
        List<Registry> expens = expenseSearcher.search("01/06/2021","30/06/2021");
        assertTrue(expens.isEmpty());
    }
}