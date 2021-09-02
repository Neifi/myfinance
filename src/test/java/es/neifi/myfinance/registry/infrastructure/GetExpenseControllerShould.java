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

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static java.lang.String.valueOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GetExpenseController.class)
class GetExpenseControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrySearcher registrySearcher;

    @MockBean
    private UserService userService;

    @Test
    void return_http_status_200_with_existent_user_and_expense() throws Exception {
        long date = Timestamp.from(Instant.now()).getTime();
        Registry registry = Registry.createExpense(
                new UserID("053e7ba6-b1d6-4dcb-8047-bbb0cf7a0b99"),
                new RegistryID("787f28f2-003a-4445-8659-d60683107845"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(date)
        );
        Optional<User> user = Optional.of(User.builder()
                .id(new UserID("2be27975-4d87-413b-991c-ceff0bb960db"))
                .username(new UserName("some-name"))
                .email(new Email("some-email@mail.com"))
                .build());

        when(userService.search("2be27975-4d87-413b-991c-ceff0bb960db")).thenReturn(user);
        when(registrySearcher.findRegistry("787f28f2-003a-4445-8659-d60683107845")).thenReturn(java.util.Optional.of(registry));

        ResultMatcher resultMatcher = content().string("{\"userId\":\"053e7ba6-b1d6-4dcb-8047-bbb0cf7a0b99\",\"id\":\"787f28f2-003a-4445-8659-d60683107845\",\"category\":\"home\",\"name\":\"internet\",\"cost\":100.0,\"currency\":\"EUR\",\"date\":" + date + ",\"expense\":true}");
        mockMvc.perform(get("/user/2be27975-4d87-413b-991c-ceff0bb960db/expense/787f28f2-003a-4445-8659-d60683107845"))
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
        String userId = "2be27975-4d87-413b-991c-ceff0bb960db";
        long date = Timestamp.from(Instant.now()).getTime();
        Optional<User> user = Optional.of(User.builder()
                .id(new UserID(userId))
                .username(new UserName("some-name"))
                .email(new Email("some-email@mail.com"))
                .build());

        Registry registry = Registry.createExpense(
                new UserID(userId),
                new RegistryID("787f28f2-003a-4445-8659-d60683107845"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(date)
        );
        Registry registry1 = Registry.createExpense(
                new UserID(userId),
                new RegistryID("13dd4c9b-908a-4712-9799-bfa8e445db0a"),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(date)
        );

        ResultMatcher resultMatcher = content().string("{\"registryResponses\":[{\"userId\":\"" + userId + "\",\"id\":\"787f28f2-003a-4445-8659-d60683107845\",\"category\":\"home\",\"name\":\"internet\",\"cost\":100.0,\"currency\":\"EUR\",\"date\":" + date + ",\"expense\":true},{\"userId\":\"2be27975-4d87-413b-991c-ceff0bb960db\",\"id\":\"13dd4c9b-908a-4712-9799-bfa8e445db0a\",\"category\":\"home\",\"name\":\"internet\",\"cost\":100.0,\"currency\":\"EUR\",\"date\":" + date + ",\"expense\":true}],\"totalCost\":200.0,\"timePeriod\":[null,null]}");
        when(userService.search(userId)).thenReturn(user);
        when(registrySearcher.findExpenses(userId)).thenReturn(Arrays.asList(registry, registry1));

        mockMvc.perform(get("/user/" + userId + "/expense/"))
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

        String userId = "787f28f2-003a-4445-8659-d60683107845";
        long date = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime();
        Registry registry = Registry.createExpense(
                new UserID(userId),
                new RegistryID(userId),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(date)
        );

        long date1 = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime();
        Registry registry1 = Registry.createExpense(
                new UserID(userId),
                new RegistryID(userId),
                new Category("home"),
                new Name("internet"),
                new Cost(100),
                new Currency("EUR"),
                new Date(date1)
        );

        long initialDate = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                1,
                15,
                1)).getTime();

        long endDate = Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime();

        ResultMatcher resultMatcher = content().string("{\"registryResponses\":[{\"userId\":\"" + userId + "\",\"id\":\"787f28f2-003a-4445-8659-d60683107845\",\"category\":\"home\",\"name\":\"internet\",\"cost\":100.0,\"currency\":\"EUR\",\"date\":" + date + ",\"expense\":true},{\"userId\":\"787f28f2-003a-4445-8659-d60683107845\",\"id\":\"787f28f2-003a-4445-8659-d60683107845\",\"category\":\"home\",\"name\":\"internet\",\"cost\":100.0,\"currency\":\"EUR\",\"date\":" + date1 + ",\"expense\":true}],\"totalCost\":200.0,\"timePeriod\":[" + initialDate + "," + endDate + "]}");
        when(userService.search(userId)).thenReturn(user);
        when(registrySearcher.findExpenses(userId, initialDate, endDate)).thenReturn(Arrays.asList(registry, registry1));

        mockMvc.perform(get("/user/" + userId + "/expense/")
                        .param("initialDate", valueOf(initialDate))
                        .param("endDate", valueOf(endDate)))
                .andExpect(status().isOk())
                .andExpect(resultMatcher);

    }

    @Test
    void return_http_status_code_404_when_user_not_found() throws Exception {
        String date = valueOf(Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime());

        mockMvc.perform(get("/user/2be27975-4d87-413b-991c-ceff0bb960db/expenses/")
                        .param("initialDate", date)
                        .param("endDate", date))
                .andExpect(status().isNotFound());
    }

    @Test
    void return_http_status_code_404_when_expense_not_found() throws Exception {
        String userId = "2be27975-4d87-413b-991c-ceff0bb960db";
        Optional<User> user = Optional.of(User.builder()
                .id(new UserID(userId))
                .username(new UserName("some-name"))
                .email(new Email("some-email@mail.com"))
                .build());
        when(userService.search(userId)).thenReturn(user);
        String date = valueOf(Timestamp.valueOf(LocalDateTime.of(
                2021,
                6,
                30,
                15,
                1)).getTime());

        mockMvc.perform(get("/user/" + userId + "60db/expense/4e590e33-58f7-4a4a-8613-a4ca17948ae3")
                        .param("initialDate", date)
                        .param("endDate", date))
                .andExpect(status().isNotFound());
    }
}