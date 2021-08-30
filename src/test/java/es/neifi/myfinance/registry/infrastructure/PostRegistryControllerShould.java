package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({PostExpenseController.class, PostIncomeController.class})
class PostRegistryControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrySaver registrySaver;

    @MockBean
    private UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void should_return_201_created_when_expense_is_created() throws Exception {
        Mockito.when(userService.search(any())).thenReturn(Optional.of(User.builder().build()));
        Request request = new Request("home", "some-name", 100.00, "EUR", Timestamp.from(Instant.now()).getTime());

        mockMvc.perform(post("/user/980A5F79-C028-4312-B53E-6D231681E181/expense/7C122C2D-97E0-43E0-A63B-46C69E21D1D3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isCreated());

    }

    @Test
    void should_return_404_not_found_when_associated_user_is_not_found() throws Exception {
        Request request = new Request("home", "some-name", 100.00, "EUR", Timestamp.from(Instant.now()).getTime());
        mockMvc.perform(post("/user/inexistent-id/expense/7C122C2D-97E0-43E0-A63B-46C69E21D1D3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isNotFound());

    }


    void save_income_and_return_http_201_status() throws Exception {
        Request request = new Request("home","some-name",100.00,"EUR",Timestamp.from(Instant.now()).getTime());
        mockMvc.perform(post("/users/36cd48f6-5abc-43f2-9c76-a8c49979b55e/registry/income/b1841d22-9376-48b0-a524-c4901ddc4268")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isCreated());

    }
}