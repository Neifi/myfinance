package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.application.searchRegistry.RegistrySearcher;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.shared.domain.UserService;
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
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GetExpenseController.class)
class GetRegistryControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrySearcher registrySearcher;

    @MockBean
    private UserService userService;

    @Test
    void return_http_status_200_with_existent_user_and_expense() throws Exception {
        Registry registry = Registry.createIncome(
                new UserID("053e7ba6-b1d6-4dcb-8047-bbb0cf7a0b99"),
                new RegistryID("787f28f2-003a-4445-8659-d60683107845"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021")
        );
        Optional<User> user = Optional.of(User.builder()
                .id(new UserID("2be27975-4d87-413b-991c-ceff0bb960db"))
                .username(new UserName("some-name"))
                .email(new Email("some-email@mail.com"))
                .build());

        when(userService.search("2be27975-4d87-413b-991c-ceff0bb960db")).thenReturn(user);
        when(registrySearcher.searchIncome("787f28f2-003a-4445-8659-d60683107845")).thenReturn(java.util.Optional.ofNullable(registry));

        ResultMatcher resultMatcher = content().string("{\"userId\":\"053e7ba6-b1d6-4dcb-8047-bbb0cf7a0b99\",\"id\":\"787f28f2-003a-4445-8659-d60683107845\",\"category\":\"home\",\"name\":\"internet\",\"cost\":100.0,\"currency\":\"EUR\",\"date\":\"08/06/2021\"}");
        mockMvc.perform(get("/user/2be27975-4d87-413b-991c-ceff0bb960db/expenses/787f28f2-003a-4445-8659-d60683107845"))
                .andExpect(status().isOk())
                .andExpect(resultMatcher);
    }

    @Test
    void return_http_status_404_with_existent_user_but_not_expense() throws Exception {
        Optional<User> user = Optional.of(User.builder()
                .id(new UserID("2be27975-4d87-413b-991c-ceff0bb960db"))
                .username(new UserName("some-name"))
                .email(new Email("some-email@mail.com"))
                .build());
        when(userService.search("2be27975-4d87-413b-991c-ceff0bb960db")).thenReturn(user);

        mockMvc.perform(get("/user/2be27975-4d87-413b-991c-ceff0bb960db/expenses/787f28f2-003a-4445-8659-d60683107845"))
                .andExpect(status().isNotFound());
    }

    @Test
    void return_http_status_404_with_nonexistent_user() throws Exception {
        mockMvc.perform(get("/user/2be27975-4d87-413b-991c-ceff0bb960db/expenses/787f28f2-003a-4445-8659-d60683107845"))
                .andExpect(status().isNotFound());
    }

    @Test
    void return_http_status_200_with_list_of_expenses() throws Exception {
        Optional<User> user = Optional.of(User.builder()
                .id(new UserID("2be27975-4d87-413b-991c-ceff0bb960db"))
                .username(new UserName("some-name"))
                .email(new Email("some-email@mail.com"))
                .build());

        Registry registry = Registry.createIncome(
                new UserID("2be27975-4d87-413b-991c-ceff0bb960db"),
                new RegistryID("787f28f2-003a-4445-8659-d60683107845"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021")
        );
        Registry registry1 = Registry.createIncome(
                new UserID("2be27975-4d87-413b-991c-ceff0bb960db"),
                new RegistryID("13dd4c9b-908a-4712-9799-bfa8e445db0a"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021")
        );

        ResultMatcher resultMatcher = content().string("{\"expenses\":[{\"userId\":\"2be27975-4d87-413b-991c-ceff0bb960db\",\"id\":\"787f28f2-003a-4445-8659-d60683107845\",\"category\":\"home\",\"name\":\"internet\",\"cost\":100.0,\"currency\":\"EUR\",\"date\":\"08/06/2021\"},{\"userId\":\"2be27975-4d87-413b-991c-ceff0bb960db\",\"id\":\"13dd4c9b-908a-4712-9799-bfa8e445db0a\",\"category\":\"home\",\"name\":\"internet\",\"cost\":100.0,\"currency\":\"EUR\",\"date\":\"08/06/2021\"}],\"totalExpended\":200.0,\"timePeriod\":[null,null]}");
        when(userService.search("2be27975-4d87-413b-991c-ceff0bb960db")).thenReturn(user);
        when(registrySearcher.searchIncome()).thenReturn(Arrays.asList(registry, registry1));

        mockMvc.perform(get("/user/2be27975-4d87-413b-991c-ceff0bb960db/expenses/"))
                .andExpect(status().isOk())
                .andExpect(resultMatcher);


    }

    @Test
    void return_http_status_200_with_list_of_expenses_in_time_period() throws Exception {
        Optional<User> user = Optional.of(User.builder()
                .id(new UserID("2be27975-4d87-413b-991c-ceff0bb960db"))
                .username(new UserName("some-name"))
                .email(new Email("some-email@mail.com"))
                .build());

        Registry registry = Registry.createIncome(
                new UserID("787f28f2-003a-4445-8659-d60683107845"),
                new RegistryID("787f28f2-003a-4445-8659-d60683107845"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021")
        );

        Registry registry1 = Registry.createIncome(
                new UserID("787f28f2-003a-4445-8659-d60683107845"),
                new RegistryID("787f28f2-003a-4445-8659-d60683107845"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date("08/06/2021")
        );

        ResultMatcher resultMatcher = content().string("{\"expenses\":[{\"userId\":\"787f28f2-003a-4445-8659-d60683107845\",\"id\":\"787f28f2-003a-4445-8659-d60683107845\",\"category\":\"home\",\"name\":\"internet\",\"cost\":100.0,\"currency\":\"EUR\",\"date\":\"08/06/2021\"},{\"userId\":\"787f28f2-003a-4445-8659-d60683107845\",\"id\":\"787f28f2-003a-4445-8659-d60683107845\",\"category\":\"home\",\"name\":\"internet\",\"cost\":100.0,\"currency\":\"EUR\",\"date\":\"08/06/2021\"}],\"totalExpended\":200.0,\"timePeriod\":[\"01/06/2021\",\"31/06/2021\"]}");
        when(userService.search("2be27975-4d87-413b-991c-ceff0bb960db")).thenReturn(user);
        when(registrySearcher.searchIncome("01/06/2021", "31/06/2021")).thenReturn(Arrays.asList(registry, registry1));

        mockMvc.perform(get("/user/2be27975-4d87-413b-991c-ceff0bb960db/expenses/")
                .param("initialDate", "01/06/2021")
                .param("endDate", "31/06/2021"))
                .andExpect(status().isOk())
                .andExpect(resultMatcher);

    }

    @Test
    void return_http_status_code_404_when_user_not_found() throws Exception {
        mockMvc.perform(get("/user/2be27975-4d87-413b-991c-ceff0bb960db/expenses/")
                .param("initialDate", "01/06/2021")
                .param("endDate", "31/06/2021"))
                .andExpect(status().isNotFound());
    }

}