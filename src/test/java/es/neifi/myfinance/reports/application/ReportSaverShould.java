package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.registry.application.saveRegistry.SaveRegistryRequest;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.registry.domain.vo.*;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class ReportSaverShould {

    private RegistryRepository registryRepository = Mockito.mock(RegistryRepository.class);
    private ReportRepository reportRepository = Mockito.mock(ReportRepository.class);
    private EventBus eventBus = Mockito.mock(EventBus.class);

    private RegistrySaver registrySaver = new RegistrySaver(registryRepository,eventBus);
    private ReportSaver reportSaver = new ReportSaver(reportRepository);



    @Test
    void should_save_report_when_registry_is_saved() throws ParseException {
        String id = "70c0b2ff-d376-48aa-b43f-57a827f79316";
        String category = "some-category";
        String currency = "EUR";
        String date = "27/11/2021";
        String name = "some-name";
        double cost = 1304.54;
        boolean isExpense = true;

        Registry expense = Registry
                .create(new RegistryID(id),
                        new Category(category),
                        new Name(name),
                        new Cost(cost),
                        new Currency(currency),
                        new Date(date),
                        isExpense);



        when(reportRepository.findLast()).thenReturn(Optional.of(Report.builder()
                .totalIncomes(1000)
                .totalSavings(1000)
                .reportID(new ReportID("c295aded-15bd-4d11-9299-2e36dcbee724"))
                .date(new Date("30/06/2021"))
                .totalExpenses(500)
                .build()));


        Mockito.verify(reportRepository, times(1)).findLast();

    }



}