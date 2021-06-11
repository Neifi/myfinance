package es.neifi.myfinance.users.infrastructure.postRegisterUserController;

import es.neifi.myfinance.users.application.register.UserRegistrator;
import es.neifi.myfinance.users.domain.UserRepository;
import es.neifi.myfinance.users.infrastructure.PostRegisterUserController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PostRegisterUserController.class)
final class PostRegisterUserControllerShould {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserRegistrator userRegistrator;


    @Test
    public void should_return_201_created_when_user_is_registered() throws Exception {
        mockMvc.perform(post("/users/6657B41E-FBA8-486C-9F4C-474B351514D1")
                .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\": \"some-id\",\"name\": \"some-name\", \"email\": \"some-email\"}"))
        .andExpect(status().isCreated());
    }

}