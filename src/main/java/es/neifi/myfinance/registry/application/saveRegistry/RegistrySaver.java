package es.neifi.myfinance.registry.application.saveRegistry;

import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class RegistrySaver {

    @Autowired
    private RegistryRepository registryRepository;

    public RegistrySaver(RegistryRepository registryRepository) {
        this.registryRepository = registryRepository;
    }

    public void save(SaveRegistryRequest request) throws ParseException {
        Registry registry = Registry.builder()
                .id(new RegistryID(request.id()))
                .category(new Category(request.category()))
                .name(new Name(request.name()))
                .cost(new Cost(request.cost()))
                .currency(new Currency(request.currency()))
                .date(new Date(request.date()))
                .isExpense(request.isExpense())
                .build();
        registryRepository.save(registry);
    }
}
