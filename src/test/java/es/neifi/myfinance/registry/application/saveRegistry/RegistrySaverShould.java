package es.neifi.myfinance.registry.application.saveRegistry;

import es.neifi.myfinance.registry.domain.RegistryCreatedDomainEvent;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.*;
import es.neifi.myfinance.shared.domain.bus.event.DomainEvent;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

class RegistrySaverShould {

    private RegistryRepository registryRepository = Mockito.mock(RegistryRepository.class);
    private EventBus eventBus = Mockito.mock(EventBus.class);


    private RegistrySaver registrySaver = new RegistrySaver(registryRepository, eventBus);


    @Test
    public void save_income_successfully() throws ParseException {
        SaveRegistryRequest request = SaveRegistryRequest.builder()
                .id("70c0b2ff-d376-48aa-b43f-57a827f79316")
                .category("some-category")
                .currency("EUR")
                .date("27/11/2021")
                .name("some-name")
                .cost(1304.54)
                .build();

        Registry income = Registry.createIncome(
                new RegistryID(request.id()),
                new Category(request.category()), new Name(request.name()),
                new Cost(request.cost()),
                new Currency(request.currency()),
                new Date(request.date()));

        registrySaver.saveIncome(request);

        Mockito.verify(registryRepository, Mockito.times(1)).save(income);
    }

    @Test
    public void save_expense_successfully() throws ParseException {
        SaveRegistryRequest request = SaveRegistryRequest.builder()
                .id("70c0b2ff-d376-48aa-b43f-57a827f79316")
                .category("some-category")
                .currency("EUR")
                .date("27/11/2021")
                .name("some-name")
                .cost(1304.54)
                .build();

        Registry expense = Registry.createExpense(
                new RegistryID(request.id()),
                new Category(request.category()), new Name(request.name()),
                new Cost(request.cost()),
                new Currency(request.currency()),
                new Date(request.date()));

        registrySaver.saveExpense(request);

        Mockito.verify(registryRepository, Mockito.times(1)).save(expense);
    }

    @Test
    public void publish_event_when_expense_is_saved() throws ParseException {
        String id = "70c0b2ff-d376-48aa-b43f-57a827f79316";
        String category = "some-category";
        String currency = "EUR";
        String date = "27/11/2021";
        String name = "some-name";
        double cost = 1304.54;
        boolean isExpense = true;
        SaveRegistryRequest request = SaveRegistryRequest.builder()
                .id(id)
                .category(category)
                .currency(currency)
                .date(date)
                .name(name)
                .cost(cost)
                .build();

        Registry expense = Registry
                .createExpense(new RegistryID(id),
                        new Category(category),
                        new Name(name),
                        new Cost(cost),
                        new Currency(currency),
                        new Date(date));



        List<DomainEvent<?>> events = new ArrayList<>();
        events.add(new RegistryCreatedDomainEvent(
                id,
                category,
                name,
                cost,
                currency,
                date,
                isExpense));


        registrySaver.saveExpense(request);

        Mockito.verify(eventBus, Mockito.times(1)).publish(events);


    }

    @Test
    public void publish_event_when_income_is_saved() throws ParseException {
        String id = "70c0b2ff-d376-48aa-b43f-57a827f79316";
        String category = "some-category";
        String currency = "EUR";
        String date = "27/11/2021";
        String name = "some-name";
        double cost = 1304.54;
        boolean isExpense = false;

        SaveRegistryRequest request = SaveRegistryRequest.builder()
                .id(id)
                .category(category)
                .currency(currency)
                .date(date)
                .name(name)
                .cost(cost)
                .build();

        Registry expense = Registry
                .createExpense(new RegistryID(id),
                        new Category(category),
                        new Name(name),
                        new Cost(cost),
                        new Currency(currency),
                        new Date(date));



        List<DomainEvent<?>> events = new ArrayList<>();
        events.add(new RegistryCreatedDomainEvent(
                id,
                category,
                name,
                cost,
                currency,
                date,
                isExpense));

        registrySaver.saveIncome(request);

        Mockito.verify(eventBus, Mockito.times(1)).publish(events);


    }


}