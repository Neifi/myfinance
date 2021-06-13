package es.neifi.myfinance.shared.application.calculations;

import es.neifi.myfinance.registry.domain.vo.Registry;

import java.util.List;

public class MoneyCalculator {

    public static double calculate(List<Registry> expens){
        return expens.stream().map(Registry::cost)
                .reduce(0D, Double::sum);

    }


}
