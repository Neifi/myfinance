package es.neifi.myfinance.registry.application.saveRegistry;

import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.*;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class RegistrySaver  {

    @Autowired
    private RegistryRepository registryRepository;
    @Autowired
    private EventBus eventBus;
    public RegistrySaver(RegistryRepository registryRepository,EventBus eventBus) {
        this.registryRepository = registryRepository;
        this.eventBus = eventBus;

    }


    public void saveIncome(SaveRegistryRequest request) throws ParseException {

        Registry registry = Registry.createIncome(
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
