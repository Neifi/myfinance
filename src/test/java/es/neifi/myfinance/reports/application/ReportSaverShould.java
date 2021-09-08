package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.RegistryCreatedDomainEvent;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.reports.domain.IsExpense;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.reports.domain.TotalExpenses;
import es.neifi.myfinance.reports.domain.TotalIncomes;
import es.neifi.myfinance.reports.domain.TotalSavings;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.domain.UserID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportSaverShould {

    private final ReportRepository reportRepository = mock(ReportRepository.class);
    private final UserService userService = mock(UserService.class);
    private final ReportService reportService = mock(ReportService.class);
    private final ReportSaver reportSaver = new ReportSaver(reportRepository,userService,reportService);

    @Test
    void save_expense_report_on_receive_registry_created_event() {
        String id = "8c5f74c4-41b8-47b5-82ff-ec5f784add04";
        String userID = "1c9dee02-7d09-419d-ab22-70fbb8825ba2";
        String category = "some-cat";
        String name = "some-name";
        int cost = 100;
        String currency = "EUR";
        long date = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime();

        Report report = Report.create(
                new ReportID(id),
                new UserID(userID),
                new TotalExpenses(cost),
                new TotalIncomes(0.0),
                new TotalSavings(0.0),
                new IsExpense(true),
                new Date(date));



        RegistryCreatedDomainEvent registryCreatedDomainEvent = new RegistryCreatedDomainEvent(
                userID, id, category, name, cost, currency, date, true
        );

        reportSaver.on(registryCreatedDomainEvent);
        verify(reportRepository, times(1)).saveReport(report);
    }

    @Test
    void save_expense_report_one_time_although_the_event_is_duplicated() {

        String id = "8c5f74c4-41b8-47b5-82ff-ec5f784add04";
        String userID = "1c9dee02-7d09-419d-ab22-70fbb8825ba2";
        long date = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime();

        Report report = Report.create(
                new ReportID(id),
                new UserID(userID),
                new TotalExpenses(100.0),
                new TotalIncomes(0.0),
                new TotalSavings(0.0),
                new IsExpense(true),
                new Date(date));

        String category = "some-cat";
        String name = "some-name";
        int cost = 100;
        String currency = "EUR";


        RegistryCreatedDomainEvent registryCreatedDomainEvent = new RegistryCreatedDomainEvent(
                userID, id, category, name, cost, currency, date, true
        );

        reportSaver.on(registryCreatedDomainEvent);
        when(reportService.findReport(id)).thenReturn(Optional.of(report));
        reportSaver.on(registryCreatedDomainEvent);

        verify(reportRepository, Mockito.times(1)).saveReport(report);

    }



}