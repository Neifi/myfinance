package es.neifi.myfinance.expenses.infrastructure;

import es.neifi.myfinance.expenses.application.saveExpense.ExpenseSaver;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostExpenseController.class)
class PostExpenseControllerShould {

   @Autowired
    private MockMvc mockMvc;

   @MockBean
   private ExpenseSaver expenseSaver;

   @MockBean
   private UserService userService;

   @MockBean
   UserRepository userRepository;

    @Test
    void should_return_201_created_when_expense_is_created() throws Exception {
        Mockito.when(userService.search(any())).thenReturn(Optional.of(User.builder().build()));

        mockMvc.perform(post("/users/980A5F79-C028-4312-B53E-6D231681E181/registry/expenses/7C122C2D-97E0-43E0-A63B-46C69E21D1D3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"category\": \"home\",\"name\": \"some-name\", \"cost\": 100,\"currency\": \"EUR\",\"date\": \"08/06/2021\"}"))
                .andExpect(status().isCreated());

    }
    @Test
    void should_return_404_created_when_associated_user_is_not_found() throws Exception {
        mockMvc.perform(post("/users/inexistent-id/registry/expenses/7C122C2D-97E0-43E0-A63B-46C69E21D1D3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"category\": \"home\",\"name\": \"some-name\", \"cost\": 100,\"currency\": \"EUR\",\"date\": \"08/06/2021\"}"))
                .andExpect(status().isNotFound());

    }
}