package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.application.ReportFinder;
import es.neifi.myfinance.reports.application.ReportSaver;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GetReportController.class)
class GetReportControllerShould {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReportSaver reportSaver;
    
    @MockBean
    ReportFinder reportFinder;

    @Test
    void response_with_http_status_200_with_report_as_body() throws Exception {
        mockMvc.perform(get("/user/53a3e0d7-cc4a-4bd2-b9d9-207a610da974/report"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"totalExpenses\":100,\"totalIncomes\":1100,\"totalSavings\":1000,\"date\":\"22/06/2021}\"}"));


    }

    @Test
    void response_with_http_status_404_when_user_not_found() throws Exception {
        mockMvc.perform(get("/user/53a3e0d7-cc4a-4bd2-b9d9-207a610da974/report"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"totalExpenses\":100,\"totalIncomes\":1100,\"totalSavings\":1000,\"date\":\"22/06/2021}\"}"));


    }



}
