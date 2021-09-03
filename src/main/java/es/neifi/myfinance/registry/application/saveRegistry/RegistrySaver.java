package es.neifi.myfinance.registry.application.saveRegistry;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import es.neifi.myfinance.users.application.UserNotFoundException;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;

import java.util.Optional;

public class RegistrySaver {

    private final RegistryRepository registryRepository;
    private final UserService userService;
    private final EventBus eventBus;

    public RegistrySaver(RegistryRepository registryRepository, EventBus eventBus, UserService userService) {
        this.registryRepository = registryRepository;
        this.eventBus = eventBus;
        this.userService = userService;
    }

    public void saveIncome(SaveRegistryCommand saveRegistryCommand) {

        findUser(saveRegistryCommand);

        Registry registry = Registry.createIncome(
                new UserID(saveRegistryCommand.userId()),
                new RegistryID(saveRegistryCommand.id()),
                new Category(saveRegistryCommand.category()),
                new Name(saveRegistryCommand.name()),
                new Cost(saveRegistryCommand.cost()),
                new Currency(saveRegistryCommand.currency()),
                new Date(saveRegistryCommand.date()));


        registryRepository.save(registry);
        this.eventBus.publish(registry.pullEvents());
    }

    public void saveExpense(SaveRegistryCommand saveRegistryCommand) throws UserNotFoundException{

        findUser(saveRegistryCommand);

        Registry registry = Registry.createExpense(
                new UserID(saveRegistryCommand.userId()),
                new RegistryID(saveRegistryCommand.id()),
                new Category(saveRegistryCommand.category()),
                new Name(saveRegistryCommand.name()),
                new Cost(saveRegistryCommand.cost()),
                new Currency(saveRegistryCommand.currency()),
                new Date(saveRegistryCommand.date()));

        registryRepository.save(registry);
        this.eventBus.publish(registry.pullEvents());
    }

    private void findUser(SaveRegistryCommand request) throws UserNotFoundException{
        Optional<User> user = this.userService.find(request.userId());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with id:" + request.userId());
        }
    }


}
