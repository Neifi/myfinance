package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.domain.vo.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class InMemoryRegistryRepository implements RegistryRepository {

    private HashMap<String, Registry> expenses = new HashMap<>();

    public InMemoryRegistryRepository() {

    }

    @Override
    public void save(Registry registry) {
        expenses.put(registry.getId().value(), registry);
    }

    @Override
    public Optional<Registry> search(String id) {
        return Optional.ofNullable(expenses.get(id));
    }

    @Override
    public List<Registry> search() {
        return new ArrayList<>(expenses.values());
    }

}
