package es.neifi.myfinance.incomes.application.IncomeSaver;

import es.neifi.myfinance.expenses.domain.vo.Category;
import es.neifi.myfinance.expenses.domain.vo.Currency;
import es.neifi.myfinance.expenses.domain.vo.Date;
import es.neifi.myfinance.expenses.domain.vo.Name;
import es.neifi.myfinance.incomes.domain.Income;
import es.neifi.myfinance.incomes.domain.IncomeID;
import es.neifi.myfinance.incomes.domain.IncomeRepository;
import es.neifi.myfinance.incomes.domain.Retribution;

import java.text.ParseException;

public class IncomeSaver {
    private final IncomeRepository incomeRepository
            ;

    public IncomeSaver(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public void save(SaveIncomeRequest request) throws ParseException {
        Income income = Income.builder()
                .incomeID(new IncomeID(request.getId()))
                .category(new Category(request.getCategory()))
                .name(new Name(request.getName()))
                .retribution(new Retribution(request.getRetribution()))
                .currency(new Currency(request.getCurrency()))
                .date(new Date(request.getDate()))
                .build();
        this.incomeRepository.save(income);
    }
}
