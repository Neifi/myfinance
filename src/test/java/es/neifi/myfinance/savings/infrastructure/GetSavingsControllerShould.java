package es.neifi.myfinance.savings.infrastructure;

import es.neifi.myfinance.utils.ExpectedResult;
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
@WebMvcTest(GetSavingsController.class)
class GetSavingsControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_200_with_a_list_of_savings() throws Exception {
        mockMvc.perform(get("/users/ee9b38cd-b01b-4847-9eda-18ba4b2bb1c3/registry/savings"))
        .andExpect(status().isOk())
        .andExpect(content().string(ExpectedResult.SAVING_ARRAY_RESULT));
    }


    void should_return_204_when_no_savings_registered() throws Exception {
        mockMvc.perform(get("/users/ee9b38cd-b01b-4847-9eda-18ba4b2bb1c3/registry/savings"))
                .andExpect(status().isOk())
                .andExpect(content().string(ExpectedResult.SAVING_ARRAY_RESULT));
    }

    void should_return_404_when_related_user_id_not_exist(){}


}