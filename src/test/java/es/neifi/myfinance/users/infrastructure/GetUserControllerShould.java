package es.neifi.myfinance.users.infrastructure;

import es.neifi.myfinance.users.application.exceptions.UserNotFoundException;
import es.neifi.myfinance.users.application.find.UserFinder;
import es.neifi.myfinance.users.domain.Avatar;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = GetUserController.class)
final class GetUserControllerShould {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserFinder userRegistrator;


    @Test
    public void should_return_200_with_user_data() throws Exception {
        String userId = "061bddf7-dac7-4a2d-b7ab-e040bbcfd339";
        Optional user = Optional.of(User.createUser(
                        new UserID("061bddf7-dac7-4a2d-b7ab-e040bbcfd339"),
                        new UserName("username"),
                        new Email("email@mail.com"),
                        new Avatar("https://google.com")

                )
        );

        when(userRegistrator.findById(userId)).thenReturn(user);
        mockMvc.perform(get("/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"userId\": \"061bddf7-dac7-4a2d-b7ab-e040bbcfd339\",\"userName\": \"username\", \"email\": \"email@mail.com\"}"));
    }

    @Test
    public void should_return_404_when_user_is_not_found() throws Exception {
        String userId = "061bddf7-dac7-4a2d-b7ab-e040bbcfd339";
        when(userRegistrator.findById(userId)).thenThrow(new UserNotFoundException(userId));
        mockMvc.perform(get("/user/" + userId))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"localizedMessage\": \"User not found with ID: 061bddf7-dac7-4a2d-b7ab-e040bbcfd339\"}"));
    }
}