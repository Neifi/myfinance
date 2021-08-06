package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;

import java.util.List;
import java.util.Optional;

public class PostgresRegistryRepository implements RegistryRepository {
    @Override
    public void save(Registry registry) {

    }

    @Override
    public List<Registry> search() {
        return null;
    }

    @Override
    public List<Registry> searchIncomes() {
        return null;
    }

    @Override
    public List<Registry> searchExpenses() {
        return null;
    }

    @Override
    public List<Registry> searchExpenseInRange(String initialRange, String endRange) {
        return null;
    }

    @Override
    public List<Registry> searchIncomeInRange(String initialRange, String endRange) {
        return null;
    }

    @Override
    public Optional<Registry> searchExpenseById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Registry> searchIncomeById(String id) {
        return Optional.empty();
    }
}
