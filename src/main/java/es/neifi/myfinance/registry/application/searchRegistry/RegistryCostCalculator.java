package es.neifi.myfinance.registry.application.searchRegistry;

import es.neifi.myfinance.registry.domain.Registry;

import java.util.List;

public class RegistryCostCalculator {

    public static double calculate(List<Registry> registries) {
        return registries.stream().map(registry -> registry.cost().value())
                .reduce(0D, Double::sum);
    }
}