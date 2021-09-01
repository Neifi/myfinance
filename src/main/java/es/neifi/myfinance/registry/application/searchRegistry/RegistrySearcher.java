package es.neifi.myfinance.registry.application.searchRegistry;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;

import java.util.List;
import java.util.Optional;

public class RegistrySearcher {

    private RegistryRepository registryRepository;

    public RegistrySearcher(RegistryRepository registryRepository) {
        this.registryRepository = registryRepository;
    }

    public Optional<Registry> searchRegistry(String id) {
        return registryRepository.searchRegistryById(id);
    }


    public List<Registry> searchIncomes(String userId) {

        return registryRepository.searchIncomes(userId);
    }

    public List<Registry> searchIncomes(String userId, Long initialDate, Long endDate) {
        return registryRepository.searchIncomes(userId, initialDate, endDate);
    }

    public List<Registry> searchExpenses(String userId, Long initialDate, Long endDate) {
        return registryRepository.searchExpenses(userId, initialDate, endDate);
    }

    public List<Registry> searchExpenses(String userId) {
        return registryRepository.searchExpenses(userId);

    }

    public List<Registry> searchRegistry(String userId, Long intialDate, Long endDate) {
        return registryRepository.searchIncomes(userId, intialDate, endDate);

    }
}
