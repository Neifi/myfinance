package es.neifi.myfinance.registry.domain;

import java.util.List;
import java.util.Optional;

public interface RegistryRepository {

    void save(Registry registry);

    List<Registry> search(String userId);

    Optional<Registry> searchRegistryById(String id);

    List<Registry> searchIncomes(String userId);

    List<Registry> searchExpenses(String userId);

    List<Registry> searchExpenses(String userId, Long initialRange, Long endRange);

    List<Registry> searchIncomes(String id, Long initialRange, Long endRange);


}
