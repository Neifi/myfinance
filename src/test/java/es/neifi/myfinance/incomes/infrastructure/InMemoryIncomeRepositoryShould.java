package es.neifi.myfinance.incomes.infrastructure;

import es.neifi.myfinance.expenses.domain.vo.Category;
import es.neifi.myfinance.expenses.domain.vo.Currency;
import es.neifi.myfinance.expenses.domain.vo.Date;
import es.neifi.myfinance.expenses.domain.vo.Name;
import es.neifi.myfinance.incomes.domain.Income;
import es.neifi.myfinance.incomes.domain.IncomeID;
import es.neifi.myfinance.incomes.domain.IncomeRepository;
import es.neifi.myfinance.incomes.domain.Retribution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryIncomeRepositoryShould {

    IncomeRepository incomeRepository = new InMemoryIncomeRepository();

    @Test
    void save_income_to_db() throws ParseException {
        Income income = Income.builder()
                .incomeID(new IncomeID("8b222b6a-b851-4fe4-adab-e3bc4810e2fc"))
                .name(new Name("some-name"))
                .category(new Category("some-category"))
                .currency(new Currency("EUR"))
                .retribution(new Retribution(1603.98))
                .date(new Date("16/06/2021"))
                .build();
        incomeRepository.save(income);
    }

    @Test
    void find_existent_income_in_db() throws ParseException {
        String id = "8b222b6a-b851-4fe4-adab-e3bc4810e2fc";
        Income expectedIncome = Income.builder()
                .incomeID(new IncomeID(id))
                .name(new Name("some-name"))
                .category(new Category("some-category"))
                .currency(new Currency("EUR"))
                .retribution(new Retribution(1603.98))
                .date(new Date("16/06/2021"))
                .build();
        incomeRepository.save(expectedIncome);
        Optional<Income> income = incomeRepository.search(id);

        assertEquals(Optional.of(expectedIncome),income);

    }

    @Test
    void not_find_nonexistent_income_in_db(){
        Optional<Income> income = incomeRepository.search("nonexistent-id");

        assertFalse(income.isPresent());
    }
}