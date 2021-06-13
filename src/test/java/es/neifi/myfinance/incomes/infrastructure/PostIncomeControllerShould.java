package es.neifi.myfinance.incomes.infrastructure;

import es.neifi.myfinance.incomes.application.IncomeSaver.IncomeSaver;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostIncomeController.class)
class PostIncomeControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IncomeSaver incomeSaver;

    @Test
    void save_income_and_return_http_201_status() throws Exception {

        mockMvc.perform(post("/users/36cd48f6-5abc-43f2-9c76-a8c49979b55e/registry/incomes/b1841d22-9376-48b0-a524-c4901ddc4268")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"category\": \"home\",\"name\": \"some-name\", \"cost\": 100,\"currency\": \"EUR\",\"date\": \"08/06/2021\"}"))
                .andExpect(status().isCreated());

    }
}