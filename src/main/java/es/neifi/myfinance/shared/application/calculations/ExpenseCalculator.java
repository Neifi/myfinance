package es.neifi.myfinance.shared.application.calculations;

import es.neifi.myfinance.registry.domain.vo.Registry;
import es.neifi.myfinance.savings.domain.Saving;

import java.util.List;

public class ExpenseCalculator{

    public static double calculate(List<Registry> expens){
        return expens.stream().map(Registry::cost)
                .reduce(0D, Double::sum);

    }



}
