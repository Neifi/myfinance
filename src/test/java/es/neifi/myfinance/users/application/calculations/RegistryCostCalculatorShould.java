package es.neifi.myfinance.users.application.calculations;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.*;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryCostCalculator;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistryCostCalculatorShould {

    @Test
    void calculate_total_amount() throws ParseException {
        Registry registry = Registry.builder()
                .id(new RegistryID("787f28f2-003a-4445-8659-d60683107845"))
                .category(new Category("home"))
                .name(new Name("internet"))
                .cost(new Cost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Registry registry1 = Registry.builder()
                .id(new RegistryID("787f28f2-003a-4445-8659-d60683107845"))
                .category(new Category("home"))
                .name(new Name("internet"))
                .cost(new Cost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();
        Registry registry2 = Registry.builder()
                .id(new RegistryID("787f28f2-003a-4445-8659-d60683107845"))
                .category(new Category("home"))
                .name(new Name("internet"))
                .cost(new Cost(100))
                .currency(new Currency("EUR"))
                .date(new Date("08/06/2021"))
                .build();

        List<Registry> expens = Arrays.asList(registry, registry1, registry2);

        double total = RegistryCostCalculator.calculate(expens);

        assertEquals(300,total);
    }

}