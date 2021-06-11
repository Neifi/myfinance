package es.neifi.myfinance.expenses.infrastructure;

import es.neifi.myfinance.expenses.application.saveExpense.ExpenseSaver;
import es.neifi.myfinance.users.application.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostExpensesController.class)
class PostExpensesControllerShould {

   @Autowired
    private MockMvc mockMvc;

   @MockBean
   private ExpenseSaver expenseSaver;

   @MockBean
   private UserService userService;

    @Test
    void should_return_201_created_when_expense_is_created() throws Exception {
        mockMvc.perform(post("/users/980A5F79-C028-4312-B53E-6D231681E181/registry/expenses/7C122C2D-97E0-43E0-A63B-46C69E21D1D3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"category\": \"home\",\"name\": \"some-name\", \"cost\": 100,\"currency\": \"EUR\",\"date\": \"08/06/2021\"}"))
                .andExpect(status().isCreated());
    }
    @Test
    void should_return_404_created_when_associated_user_is_not_found() throws Exception {
        Mockito.when(userService.search("inexistent-id")).thenReturn(Optional.empty());
        mockMvc.perform(post("/users/inexistent-id/registry/expenses/7C122C2D-97E0-43E0-A63B-46C69E21D1D3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"category\": \"home\",\"name\": \"some-name\", \"cost\": 100,\"currency\": \"EUR\",\"date\": \"08/06/2021\"}"))
                .andExpect(status().isNotFound());
    }
}