package es.neifi.myfinance.registries.application.saveRegistry;

import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.registry.application.saveRegistry.SaveRegistryRequest;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;

class RegistrySaverShould {

    private RegistryRepository registryRepository = Mockito.mock(RegistryRepository.class);

    private RegistrySaver registrySaver = new RegistrySaver(registryRepository);

    @Test
    public void save_registry_successfully() throws ParseException {
        SaveRegistryRequest request = SaveRegistryRequest.builder()
                .id("70c0b2ff-d376-48aa-b43f-57a827f79316")
                .category("some-category")
                .currency("EUR")
                .date("27/11/2021")
                .name("some-name")
                .cost(1304.54)
                .build();

        Registry expense = Registry.builder()
                .id(new RegistryID(request.id()))
                .category(new Category(request.category()))
                .currency(new Currency(request.currency()))
                .date(new Date(request.date()))
                .name(new Name(request.name()))
                .cost(new Cost(request.cost()))
                .isExpense(request.isExpense())
                .build();

        registrySaver.save(request);

        Mockito.verify(registryRepository,Mockito.times(1)).save(expense);
    }


}