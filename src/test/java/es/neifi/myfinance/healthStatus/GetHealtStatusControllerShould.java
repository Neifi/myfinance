package es.neifi.myfinance.healthStatus;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = GetHealthStatusController.class)
public class GetHealtStatusControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void return_success_status() throws Exception {
        mockMvc.perform(get("/health")
                .contentType("application/json"))
                .andExpect(content().string("{\"status\":\"ok\"}"))
                .andExpect(status().isOk());

    }

}
