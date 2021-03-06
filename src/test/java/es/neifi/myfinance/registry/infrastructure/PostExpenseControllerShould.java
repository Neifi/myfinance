package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.application.saveRegistry.RegistrySaver;
import es.neifi.myfinance.shared.domain.UserService;
import es.neifi.myfinance.users.application.exceptions.UserNotFoundException;
import es.neifi.myfinance.users.domain.Avatar;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({PostExpenseController.class})
class PostExpenseControllerShould {

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
        when(userService.find(any())).thenReturn(Optional.of(User.createUser(
                new UserID(UUID.randomUUID().toString()),
                new UserName("username"),
                new Email("test@mail.com"),
                new Avatar("https://google.com")
        )));

        Request request = new Request("home", "some-name", 100.00, "EUR", Timestamp.from(Instant.now()).getTime());

        mockMvc.perform(post("/user/980A5F79-C028-4312-B53E-6D231681E181/expense/7C122C2D-97E0-43E0-A63B-46C69E21D1D3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isCreated());

    }

    @Test
    void should_return_404_not_found_when_associated_user_is_not_found() throws Exception {
        when(userService.find("some-id")).thenThrow(new UserNotFoundException("some-id"));
        Request request = new Request("home", "some-name", 100.00, "EUR", Timestamp.from(Instant.now()).getTime());
        mockMvc.perform(post("/user/some-id/expense/7C122C2D-97E0-43E0-A63B-46C69E21D1D3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isNotFound());
    }

}