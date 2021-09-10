package es.neifi.myfinance.registry.application.searchRegistry;

import es.neifi.myfinance.registry.application.exceptions.RegistryNotFoundException;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.shared.domain.UserService;

import java.util.List;
import java.util.Optional;

public class RegistrySearcher {

    private final RegistryRepository registryRepository;
    private final UserService userService;

    public RegistrySearcher(RegistryRepository registryRepository, UserService userService) {
        this.registryRepository = registryRepository;
        this.userService = userService;
    }

    public Optional<Registry> findRegistry(String registryId) {

        Optional<Registry> optionalRegistry = registryRepository.searchRegistryById(registryId);
        if (optionalRegistry.isEmpty()){
            throw new RegistryNotFoundException(registryId);
        }
        return optionalRegistry;
    }

    public List<Registry> findIncomes(String userId) {

        return registryRepository.searchIncomes(userId);
    }

    public List<Registry> findIncomes(String userId, Long initialDate, Long endDate) {
        return registryRepository.searchIncomes(userId, initialDate, endDate);
    }

    public List<Registry> findExpenses(String userId, Long initialDate, Long endDate) {
        return registryRepository.searchExpenses(userId, initialDate, endDate);
    }

    public List<Registry> findExpenses(String userId) {
        return registryRepository.searchExpenses(userId);

    }

    public List<Registry> findRegistry(String userId, Long intialDate, Long endDate) {
        return registryRepository.searchIncomes(userId, intialDate, endDate);

    }
}
