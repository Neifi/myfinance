package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.*;
import es.neifi.myfinance.users.domain.UserID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRegistryRepositoryShould {

    private RegistryRepository inMemoryRegistryRepository;

    @BeforeEach
    public void setup() {
        inMemoryRegistryRepository = new InMemoryRegistryRepository();
    }

    @Test
    void save_expense_successfully_in_db() throws ParseException {
        Registry expense = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
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
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
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
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID(id),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/6/2021"));

        inMemoryRegistryRepository.save(expectedRegistry);
        Optional<Registry> expense = inMemoryRegistryRepository.searchExpenseById(id.toLowerCase());

        assertTrue(expense.isPresent());
        assertEquals(expense, Optional.of(expectedRegistry));

    }

    @Test
    void search_income_with_existent_id() throws ParseException {
        String id = "77335C39-B709-4176-989D-9800216DC0C7";
        Registry expectedRegistry = Registry.createIncome(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID(id),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/6/2021"));

        inMemoryRegistryRepository.save(expectedRegistry);
        Optional<Registry> expense = inMemoryRegistryRepository.searchIncomeById(id.toLowerCase());

        assertTrue(expense.isPresent());
        assertEquals(expense, Optional.of(expectedRegistry));

    }

    @Test
    void not_return_non_existing_expense() {

        Optional<Registry> expense = inMemoryRegistryRepository.searchExpenseById("non-existing-id".toLowerCase());

        assertFalse(expense.isPresent());

    }

    @Test
    void find_expense_between_dates() throws ParseException {

        Registry expense = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID("679142d5-7195-4908-8dd5-8b55f31174dd"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/06/2021"));

        Registry expense2 = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID("fefee508-fa47-426e-a387-6eb7ce6b5c49"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("13/06/2021"));

        Registry expense3 = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID("52742e59-efd0-4f5f-959a-9ba4daf93b22"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/05/2021"));

        inMemoryRegistryRepository.save(expense);
        inMemoryRegistryRepository.save(expense2);
        inMemoryRegistryRepository.save(expense3);

        List<Registry> expenses = inMemoryRegistryRepository.searchExpenseInRange("01/06/2021", "14/06/2021");

        assertEquals(2, expenses.size());
        assertTrue(expenses.contains(expense));
        assertTrue(expenses.contains(expense2));

    }

    @Test
    void find_income_between_dates() throws ParseException {

        Registry income = Registry.createIncome(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID("679142d5-7195-4908-8dd5-8b55f31174dd"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/06/2021"));

        Registry income1 = Registry.createIncome(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID("fefee508-fa47-426e-a387-6eb7ce6b5c49"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("13/06/2021"));

        Registry income2 = Registry.createIncome(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID("52742e59-efd0-4f5f-959a-9ba4daf93b22"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/05/2021"));

        inMemoryRegistryRepository.save(income);
        inMemoryRegistryRepository.save(income1);
        inMemoryRegistryRepository.save(income2);

        List<Registry> expenses = inMemoryRegistryRepository.searchIncomeInRange("01/06/2021", "14/06/2021");

        assertEquals(2, expenses.size());
        assertTrue(expenses.contains(income));
        assertTrue(expenses.contains(income1));

    }

    @Test
    void find_expenses_in_date_order() throws ParseException {
        Registry expense = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID("679142d5-7195-4908-8dd5-8b55f31174dd"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/06/2021"));

        Registry expense2 = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID("fefee508-fa47-426e-a387-6eb7ce6b5c49"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("13/06/2021"));

        Registry expense3 = Registry.createExpense(
                new UserID("94c8208e-b116-49a8-bf6e-0560135dffb4"),
                new RegistryID("52742e59-efd0-4f5f-959a-9ba4daf93b22"),
                new Category("some-cat"),
                new Name("some-name"),
                new Cost(100),
                new Currency("EUR"),
                new Date("12/05/2021"));

        inMemoryRegistryRepository.save(expense3);
        inMemoryRegistryRepository.save(expense2);
        inMemoryRegistryRepository.save(expense);

        List<Registry> expenses = inMemoryRegistryRepository.searchExpenses();

        assertEquals(3, expenses.size());
        assertEquals(expense, expenses.get(1));
        assertEquals(expense2, expenses.get(0));
        assertEquals(expense3, expenses.get(2));
    }
}