package es.neifi.myfinance.registry.domain;

import es.neifi.myfinance.registry.domain.vo.Registry;

import java.util.List;
import java.util.Optional;

public interface RegistryRepository {

    void save(Registry registry);
    Optional<Registry> search(String id);

    List<Registry> search();

}
