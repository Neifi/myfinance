package es.neifi.myfinance.registry.application.saveRegistry;

import es.neifi.myfinance.registry.domain.RegistryCreatedDomainEvent;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.*;
import es.neifi.myfinance.shared.application.registry.RegistryMediator;
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
    public void save_registry_successfully() throws ParseException {
        SaveRegistryRequest request = SaveRegistryRequest.builder()
                .id("70c0b2ff-d376-48aa-b43f-57a827f79316")
                .category("some-category")
                .currency("EUR")
                .date("27/11/2021")
                .name("some-name")
                .cost(1304.54)
                .build();

        Registry expense = Registry.builder()
                .id(new RegistryID(request.id()))
                .category(new Category(request.category()))
                .currency(new Currency(request.currency()))
                .date(new Date(request.date()))
                .name(new Name(request.name()))
                .cost(new Cost(request.cost()))
                .isExpense(request.isExpense())
                .build();

        registrySaver.save(request);

        Mockito.verify(registryRepository, Mockito.times(1)).save(expense);
    }

    @Test
    public void publish_event_when_registry_is_saved() throws ParseException {
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
                .isExpense(isExpense)
                .build();

        Registry expense = Registry
                .create(new RegistryID(id),
                        new Category(category),
                        new Name(name),
                        new Cost(cost),
                        new Currency(currency),
                        new Date(date),
                        isExpense);

        List<DomainEvent<?>> events = new ArrayList<>();
        events.add(new RegistryCreatedDomainEvent(
                id,
                category,
                name,
                cost,
                currency,
                date,
                isExpense));

        registrySaver.save(request);

        Mockito.verify(eventBus, Mockito.times(1)).publish(events);



    }


}