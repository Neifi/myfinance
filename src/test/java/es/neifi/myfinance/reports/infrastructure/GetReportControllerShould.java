package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.reports.application.ReportFinder;
import es.neifi.myfinance.reports.domain.IsExpense;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.TotalExpenses;
import es.neifi.myfinance.reports.domain.TotalIncomes;
import es.neifi.myfinance.reports.domain.TotalSavings;
import es.neifi.myfinance.users.domain.UserID;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static es.neifi.myfinance.shared.utils.DateUtils.timestampOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GetReportController.class)
class GetReportControllerShould {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ReportFinder reportFinder;

    @Test
    void response_with_http_status_200_with_expense_report_as_body() throws Exception {
        String reportId = UUID.randomUUID().toString();
        Long date = timestampOf(2021, 6, 22, 11, 33, 18);
        when(reportFinder.findById(reportId)).thenReturn(
                Optional.of(
                        Report.create(
                                new ReportID(reportId),
                                new UserID(UUID.randomUUID().toString()),
                                new TotalExpenses(100),
                                new TotalIncomes(1100),
                                new TotalSavings(1000),
                                new IsExpense(true),
                                new Date(date)
                        )
                ));
        mockMvc.perform(get("/user/report/" + reportId))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"totalExpenses\":100,\"totalExpenses\":100,\"totalIncomes\":1100,\"totalSavings\":1000,\"expense\":true,\"date\":" + date + "}"));
    }
}
