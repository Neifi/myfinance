package es.neifi.myfinance.incomes.application.IncomeSaver;

import es.neifi.myfinance.expenses.domain.vo.Category;
import es.neifi.myfinance.expenses.domain.vo.Currency;
import es.neifi.myfinance.expenses.domain.vo.Date;
import es.neifi.myfinance.expenses.domain.vo.Name;
import es.neifi.myfinance.incomes.domain.Income;
import es.neifi.myfinance.incomes.domain.IncomeID;
import es.neifi.myfinance.incomes.domain.IncomeRepository;
import es.neifi.myfinance.incomes.domain.Retribution;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;


class SaveIncomeRequestShould {

    private IncomeRepository incomeRepository = Mockito.mock(IncomeRepository.class);

    private IncomeSaver incomeSaver = new IncomeSaver(incomeRepository);

    @Test
    public void save_income_successfully() throws ParseException {
        SaveIncomeRequest request = SaveIncomeRequest.builder()
                .id("70c0b2ff-d376-48aa-b43f-57a827f79316")
                .category("some-category")
                .currency("EUR")
                .date("27/11/2021")
                .name("some-name")
                .retribution(1304.54)
                .build();

        Income income = Income.builder()
                .incomeID(new IncomeID(request.getId()))
                .category(new Category(request.getCategory()))
                .name(new Name(request.getName()))
                .retribution(new Retribution(request.getRetribution()))
                .currency(new Currency(request.getCurrency()))
                .date(new Date(request.getDate()))
                .build();

        incomeSaver.save(request);

        Mockito.verify(incomeRepository,Mockito.times(1)).save(income);
    }

}