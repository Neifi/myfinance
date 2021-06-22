package es.neifi.myfinance.shared.application.calculations;

import es.neifi.myfinance.registry.domain.vo.Registry;

import java.util.List;

public interface Calculator {
    double calculate(List<Registry> registries);


}
