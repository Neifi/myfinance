package es.neifi.myfinance.registry.domain;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RegistryRepository {

    void save(Registry registry);

    List<Registry> search();

    List<Registry> searchIncomes();

    List<Registry> searchExpenses();

    List<Registry> searchExpenseInRange(String initialRange,String endRange);

    List<Registry> searchIncomeInRange(String initialRange,String endRange);

    Optional<Registry> searchExpenseById(String id);

    Optional<Registry> searchIncomeById(String id);

}
