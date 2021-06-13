package es.neifi.myfinance.shared.application.search;

import es.neifi.myfinance.expenses.application.utils.DateParser;
import es.neifi.myfinance.expenses.domain.ExpenseRepository;
import es.neifi.myfinance.expenses.domain.vo.Date;
import es.neifi.myfinance.expenses.domain.vo.Expense;
import es.neifi.myfinance.shared.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public abstract class RegistrySearcher {

    @Autowired
    ExpenseRepository expenseRepository;

    public RegistrySearcher(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Optional<Expense> search(String id) {
        return expenseRepository.search(id);
    }

    public List<Expense> search() {

        return expenseRepository.search();
    }

    public List<Expense> search(String intialDate, String endDate) {
        DateParser dateParser = new DateParser();
        Stream<Expense> expenseStream = filterByDate(intialDate, endDate, dateParser);
        return sortByDate(dateParser, expenseStream);


    }

    private List<Expense> sortByDate(DateParser dateParser, Stream<Expense> expenseStream) {
        return expenseStream
                .sorted(Comparator.comparing(expense -> dateParser.parse(expense.getDate().getValue()))).collect(Collectors.collectingAndThen(Collectors.toList(),
                ee -> {
                    Collections.reverse(ee);
                    return ee;
                }));
    }

    private Stream<Expense> filterByDate(String initialDate, String endDate, DateParser dateParser) {
        return expenseRepository.search().stream().filter(expense ->
                dateParser.parse(expense.getDate().value())
                        .before(dateParser.parse(endDate))
                        && dateParser.parse(expense.getDate().value())
                        .after(dateParser.parse(initialDate))

        );
    }
}
