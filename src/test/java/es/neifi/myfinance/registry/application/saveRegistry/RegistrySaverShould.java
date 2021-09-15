package es.neifi.myfinance.registry.application.saveRegistry;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.RegistryCreatedDomainEvent;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.*;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.shared.domain.bus.event.AggregateID;
import es.neifi.myfinance.shared.domain.bus.event.DomainEvent;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import es.neifi.myfinance.shared.domain.bus.event.EventID;
import es.neifi.myfinance.shared.domain.bus.event.OccuredOn;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class RegistrySaverShould {

    private RegistryRepository registryRepository = Mockito.mock(RegistryRepository.class);
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private EventBus eventBus = Mockito.mock(EventBus.class);
    private UserService userService = new UserService(userRepository);

    private RegistrySaver registrySaver = new RegistrySaver(registryRepository, eventBus,userService);


    @Test
    public void save_income_successfully_with_existent_user() {
        SaveRegistryCommand request = SaveRegistryCommand.builder()
                .userId("a46ea122-f590-471e-95b8-4bc8fd46836a")
                .registryId("70c0b2ff-d376-48aa-b43f-57a827f79316")
                .category("some-category")
                .currency("EUR")
                .date(Timestamp.from(Instant.now()).getTime())
                .name("some-name")
                .cost(1304.54)
                .build();

        Registry income = Registry.createIncome(
                new UserID(request.userId()),
                new RegistryID(request.id()),
                new Category(request.category()), new Name(request.name()),
                new Cost(request.cost()),
                new Currency(request.currency()),
                new Date(request.date()));
        Mockito.when(userRepository.searchById("a46ea122-f590-471e-95b8-4bc8fd46836a"))
                .thenReturn(Optional.of(User.createUser(
                        new UserID(request.userId()),
                        new UserName("username"),
                        new Email("test@mail.com"))));
        registrySaver.saveIncome(request);

        Mockito.verify(registryRepository, Mockito.times(1)).save(income);
    }


    @Test
    public void save_expense_successfully_with_existent_user() throws ParseException {
        SaveRegistryCommand request = SaveRegistryCommand.builder()
                .userId("a46ea122-f590-471e-95b8-4bc8fd46836a")
                .registryId("70c0b2ff-d376-48aa-b43f-57a827f79316")
                .category("some-category")
                .currency("EUR")
                .date(Timestamp.from(Instant.now()).getTime())
                .name("some-name")
                .cost(1304.54)
                .build();

        Registry expense = Registry.createExpense(
                new UserID(request.userId()),
                new RegistryID(request.id()),
                new Category(request.category()), new Name(request.name()),
                new Cost(request.cost()),
                new Currency(request.currency()),
                new Date(request.date()));

        Mockito.when(userRepository.searchById("a46ea122-f590-471e-95b8-4bc8fd46836a"))
                .thenReturn(Optional.of(User.createUser(new UserID(request.userId()),new UserName("username"),new Email("test@mail.com"))));

        registrySaver.saveExpense(request);

        Mockito.verify(registryRepository, Mockito.times(1)).save(expense);
    }

    @Test
    public void publish_event_when_expense_is_saved() throws ParseException {
        String userID = "1c9dee02-7d09-419d-ab22-70fbb8825ba2";
        AggregateID id = new AggregateID("70c0b2ff-d376-48aa-b43f-57a827f79316");
        String category = "some-category";
        String currency = "EUR";
        Long date = Timestamp.from(Instant.now()).getTime();
        String name = "some-name";
        double cost = 1304.54;
        boolean isExpense = true;
        SaveRegistryCommand request = SaveRegistryCommand.builder()
                .userId(userID)
                .registryId(id.value())
                .category(category)
                .currency(currency)
                .date(date)
                .name(name)
                .cost(cost)
                .build();

        List<DomainEvent<?>> events = new ArrayList<>();
        events.add(
                new RegistryCreatedDomainEvent(
                userID,
                id,
                category,
                name,
                cost,
                currency,
                date,
                isExpense));

        Mockito.when(userRepository.searchById("1c9dee02-7d09-419d-ab22-70fbb8825ba2"))
                .thenReturn(Optional.of(User.createUser(new UserID(request.userId()),new UserName("username"),new Email("test@mail.com"))));

        registrySaver.saveExpense(request);

        Mockito.verify(eventBus, Mockito.times(1)).publish(events);


    }

    @Test
    public void publish_event_when_income_is_saved() throws ParseException {
        String userID = "1c9dee02-7d09-419d-ab22-70fbb8825ba2";
        AggregateID agregateId = new AggregateID("70c0b2ff-d376-48aa-b43f-57a827f79316");
        String category = "some-category";
        String currency = "EUR";
        long date = Timestamp.from(Instant.now()).getTime();
        String name = "some-name";
        double cost = 1304.54;
        boolean isExpense = false;

        SaveRegistryCommand request = SaveRegistryCommand.builder()
                .userId(userID)
                .registryId(agregateId.value())
                .category(category)
                .currency(currency)
                .date(date)
                .name(name)
                .cost(cost)
                .build();

        List<DomainEvent<?>> events = new ArrayList<>();
        EventID eventId = new EventID("8df3b9bf-a6e7-4f05-b8aa-64e59dd93f19");
        OccuredOn occurredOn = new OccuredOn(Timestamp.from(Instant.now()).getTime());
        events.add(new RegistryCreatedDomainEvent(

                userID,
                agregateId,
                eventId,
                occurredOn,
                category,
                name,
                cost,
                currency,
                date,
                isExpense));

        Mockito.when(userRepository.searchById("1c9dee02-7d09-419d-ab22-70fbb8825ba2"))
                .thenReturn(Optional.of(User.createUser(new UserID(request.userId()),new UserName("username"),new Email("test@mail.com"))));

        registrySaver.saveIncome(request);

        Mockito.verify(eventBus, Mockito.times(1)).publish(events);


    }


}