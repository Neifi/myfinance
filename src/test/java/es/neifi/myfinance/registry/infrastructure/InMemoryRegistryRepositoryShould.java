package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRegistryRepositoryShould {

    private RegistryRepository inMemoryRegistryRepository;

    @BeforeEach
    public void setup(){
        inMemoryRegistryRepository = new InMemoryRegistryRepository();
    }

    @Test
    void save_expense_successfully_in_db() throws ParseException {
        Registry expense = Registry.createExpense(
                new RegistryID("27483e19-c66c-4b8f-a638-2f510d39554c"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/6/2021"));

        inMemoryRegistryRepository.save(expense);
    }

    @Test
    void save_income_successfully_in_db() throws ParseException {
        Registry expense = Registry.createIncome(
                new RegistryID("27483e19-c66c-4b8f-a638-2f510d39554c"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/6/2021"));

        inMemoryRegistryRepository.save(expense);
    }

    @Test
    void search_expense_with_existent_id() throws ParseException {
        String id = "77335C39-B709-4176-989D-9800216DC0C7";
        Registry expectedRegistry = Registry.createExpense(
                new RegistryID(id),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/6/2021"));

        inMemoryRegistryRepository.save(expectedRegistry);
        Optional<Registry> expense = inMemoryRegistryRepository.searchExpenseById(id.toLowerCase());

        assertTrue(expense.isPresent());
        assertEquals(expense,Optional.of(expectedRegistry));

    }

    @Test
    void search_income_with_existent_id() throws ParseException {
        String id = "77335C39-B709-4176-989D-9800216DC0C7";
        Registry expectedRegistry = Registry.createIncome(
                new RegistryID(id),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/6/2021"));

        inMemoryRegistryRepository.save(expectedRegistry);
        Optional<Registry> expense = inMemoryRegistryRepository.searchIncomeById(id.toLowerCase());

        assertTrue(expense.isPresent());
        assertEquals(expense,Optional.of(expectedRegistry));

    }

    @Test
    void not_return_non_existing_expense() {

        Optional<Registry> expense = inMemoryRegistryRepository.searchExpenseById("non-existing-id".toLowerCase());

        assertFalse(expense.isPresent());

    }

}