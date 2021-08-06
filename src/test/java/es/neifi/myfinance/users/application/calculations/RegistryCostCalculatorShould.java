package es.neifi.myfinance.users.application.calculations;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.*;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryCostCalculator;
import es.neifi.myfinance.users.domain.UserID;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistryCostCalculatorShould {

    @Test
    void calculate_total_amount() throws ParseException {

        Registry registry = Registry.createIncome(
                new UserID("053e7ba6-b1d6-4dcb-8047-bbb0cf7a0b99"),
                new RegistryID("787f28f2-003a-4445-8659-d60683107845"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021")
        );
        Registry registry1 = Registry.createIncome(
                new UserID("5b614fe4-1de1-4d28-807f-ee9309767d53"),
                new RegistryID("13dd4c9b-908a-4712-9799-bfa8e445db0a"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021")
        );
        Registry registry2 = Registry.createIncome(
                new UserID("fdc9a3fa-eda2-463a-9648-b3950adf06df"),
                new RegistryID("d7f3bc8c-d5b7-4640-854d-ca2870c2e4ea"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021")
        );


        List<Registry> expens = Arrays.asList(registry, registry1, registry2);

        double total = RegistryCostCalculator.calculate(expens);

        assertEquals(300, total);
    }

}