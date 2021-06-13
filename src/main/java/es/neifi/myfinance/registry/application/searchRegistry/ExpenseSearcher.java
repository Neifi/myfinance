package es.neifi.myfinance.registry.application.searchRegistry;

import es.neifi.myfinance.registry.application.utils.DateParser;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.Registry;
import es.neifi.myfinance.shared.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExpenseSearcher {

    @Autowired
    RegistryRepository registryRepository;

    public ExpenseSearcher(RegistryRepository registryRepository) {
        this.registryRepository = registryRepository;
    }

    public Optional<Registry> search(String id) {
        return registryRepository.search(id);
    }

    public List<Registry> search() {

        return registryRepository.search();
    }

    public List<Registry> search(String intialDate, String endDate) {
        DateParser dateParser = new DateParser();
        Stream<Registry> expenseStream = filterByDate(intialDate, endDate, dateParser);
        return sortByDate(dateParser, expenseStream);


    }

    private List<Registry> sortByDate(DateParser dateParser, Stream<Registry> expenseStream) {
        return expenseStream
                .sorted(Comparator.comparing(expense -> dateParser.parse(expense.getDate().getValue()))).collect(Collectors.collectingAndThen(Collectors.toList(),
                ee -> {
                    Collections.reverse(ee);
                    return ee;
                }));
    }

    private Stream<Registry> filterByDate(String initialDate, String endDate, DateParser dateParser) {
        return registryRepository.search().stream().filter(expense ->
                dateParser.parse(expense.getDate().value())
                        .before(dateParser.parse(endDate))
                        && dateParser.parse(expense.getDate().value())
                        .after(dateParser.parse(initialDate))

        );
    }
}
