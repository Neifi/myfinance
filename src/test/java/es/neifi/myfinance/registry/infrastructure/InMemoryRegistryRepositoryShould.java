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
        Registry registry = Registry.builder()
                .id(new RegistryID("77335C39-B709-4176-989D-9800216DC0C7"))
                .category(new Category("Home"))
                .name(new Name("Desktop"))
                .cost(new Cost(100.40))
                .currency(new Currency("EUR"))
                .date(new Date("12/6/2021"))
                .build();

        inMemoryRegistryRepository.save(registry);
    }

    @Test
    void search_expense_with_existent_id() throws ParseException {
        Registry expectedRegistry = Registry.builder()
                .id(new RegistryID("77335C39-B709-4176-989D-9800216DC0C7"))
                .category(new Category("Home"))
                .name(new Name("Desktop"))
                .cost(new Cost(100.40))
                .currency(new Currency("EUR"))
                .date(new Date("12/06/2021"))
                .build();

        inMemoryRegistryRepository.save(expectedRegistry);
        Optional<Registry> expense = inMemoryRegistryRepository.search("77335C39-B709-4176-989D-9800216DC0C7".toLowerCase());

        assertTrue(expense.isPresent());
        assertEquals(expense,Optional.of(expectedRegistry));

    }

    @Test
    void not_return_non_existing_expense() {

        Optional<Registry> expense = inMemoryRegistryRepository.search("non-existing-id".toLowerCase());

        assertFalse(expense.isPresent());

    }
}