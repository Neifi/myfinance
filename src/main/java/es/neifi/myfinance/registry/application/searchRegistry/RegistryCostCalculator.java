package es.neifi.myfinance.registry.application.searchRegistry;

import es.neifi.myfinance.registry.domain.Registry;

import java.util.List;

public class RegistryCostCalculator {

    public static double calculate(List<Registry> expens) {
        return expens.stream().map(Registry::cost)
                .reduce(0D, Double::sum);
    }


}
