package es.neifi.myfinance.registry.application.searchRegistry;

import es.neifi.myfinance.registry.application.exceptions.RegistryNotFoundException;
import es.neifi.myfinance.registry.application.utils.DateParser;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RegistrySearcher {

    @Autowired
    RegistryRepository registryRepository;

    public RegistrySearcher(RegistryRepository registryRepository) {
        this.registryRepository = registryRepository;
    }

    public Optional<Registry> searchIncome(String id) {
        return registryRepository.searchIncomeById(id);
    }

    public Optional<Registry> searchExpense(String id) {
        return registryRepository.searchExpenseById(id);
    }


    public List<Registry> searchIncome() {

        Predicate<Registry> isExpense = Registry::isExpense;
        return registryRepository.searchIncomes();
    }

    public List<Registry> searchExpenses() {
        return registryRepository.searchExpenses();
    }

    public List<Registry> searchIncome(String intialDate, String endDate) {
        return registryRepository.searchIncomeInRange(intialDate, endDate);

    }

    public List<Registry> searchExpenses(String initialDate, String endDate) {
        return registryRepository.searchExpenseInRange(initialDate, endDate);

    }


}
