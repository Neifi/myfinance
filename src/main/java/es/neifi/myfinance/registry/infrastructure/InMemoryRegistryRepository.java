package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.application.utils.DateParser;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InMemoryRegistryRepository implements RegistryRepository {

    private final HashMap<String, Registry> registries = new HashMap<>();
    private final DateParser dateParser = new DateParser();
    public InMemoryRegistryRepository() {

    }

    @Override
    public Optional<Registry> searchExpenseById(String id) {
        Optional<Registry> registry = Optional.ofNullable(registries.get(id));

        if (registry.isPresent() && registry.get().isExpense()) {
            return registry;
        }

        return Optional.empty();
    }

    @Override
    public Optional<Registry> searchIncomeById(String id) {
        Optional<Registry> registry = Optional.ofNullable(registries.get(id));
        if (registry.isPresent() && !registry.get().isExpense()) {
            return registry;
        }

        return Optional.empty();
    }

    @Override
    public List<Registry> search() {

        return sortByDate(dateParser,new ArrayList<>(this.registries.values()));
    }

    @Override
    public void save(Registry registry) {
        registries.put(registry.getId().value(), registry);
    }

    @Override
    public List<Registry> searchIncomes() {
        Predicate<Registry> isExpense = Registry::isExpense;
        return registries.values().stream()
                .filter(isExpense.negate())
                .collect(Collectors.toList());
    }

    @Override
    public List<Registry> searchExpenses() {
        List<Registry> registries = this.registries.values().stream()
                .filter(Registry::isExpense)
                .collect(Collectors.toList());
        return sortByDate(dateParser,registries);
    }

    @Override
    public List<Registry> searchExpenseInRange(String initialRange, String endRange) {


        return filterByDate(initialRange,endRange,dateParser)
                .filter(Registry::isExpense)
                .collect(Collectors.toList());
    }

    @Override
    public List<Registry> searchIncomeInRange(String initialRange, String endRange) {
        Predicate<Registry> isExpense = Registry::isExpense;
        return filterByDate(initialRange,endRange,dateParser)
                .filter(isExpense.negate())
                .collect(Collectors.toList());
    }

    private List<Registry> sortByDate(DateParser dateParser, List<Registry> registryStream) {
        return registryStream.stream()
                .sorted(Comparator.comparing(expense -> dateParser.parse(expense.getDate().getValue())))
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        ee -> {
                            Collections.reverse(ee);
                            return ee;
                        }));
    }

    private Stream<Registry> filterByDate(String initialDate, String endDate, DateParser dateParser) {
        return registries.values().stream().filter(expense ->
                dateParser.parse(expense.getDate().value())
                        .before(dateParser.parse(endDate))
                        && dateParser.parse(expense.getDate().value())
                        .after(dateParser.parse(initialDate))

        );
    }
}
