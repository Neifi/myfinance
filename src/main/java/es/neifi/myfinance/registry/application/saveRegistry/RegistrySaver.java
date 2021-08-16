package es.neifi.myfinance.registry.application.saveRegistry;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import es.neifi.myfinance.users.domain.UserID;

import java.text.ParseException;

public class RegistrySaver  {

    private final RegistryRepository registryRepository;

    private final EventBus eventBus;
    public RegistrySaver(RegistryRepository registryRepository,EventBus eventBus) {
        this.registryRepository = registryRepository;
        this.eventBus = eventBus;

    }

    public void saveIncome(SaveRegistryRequest request) throws ParseException {

        Registry registry = Registry.createIncome(
                new UserID(request.userId()),
                new RegistryID(request.id()),
                new Category(request.category()),
                new Name(request.name()),
                new Cost(request.cost()),
                new Currency(request.currency()),
                new Date(request.date()));

        registryRepository.save(registry);
        this.eventBus.publish(registry.pullEvents());
    }
    public void saveExpense(SaveRegistryRequest request) throws ParseException {

        Registry registry = Registry.createExpense(
                new UserID(request.userId()),
                new RegistryID(request.id()),
                new Category(request.category()),
                new Name(request.name()),
                new Cost(request.cost()),
                new Currency(request.currency()),
                new Date(request.date()));

        registryRepository.save(registry);
        this.eventBus.publish(registry.pullEvents());
    }


}
