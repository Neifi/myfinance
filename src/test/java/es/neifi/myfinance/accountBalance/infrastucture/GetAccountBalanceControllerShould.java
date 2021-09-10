package es.neifi.myfinance.accountBalance.infrastucture;

import es.neifi.myfinance.accountBalance.application.AccountBalanceFinder;
import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.TotalBalance;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.users.application.UserNotFoundException;
import es.neifi.myfinance.users.domain.UserID;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Date;
import java.util.Optional;

import static es.neifi.myfinance.shared.utils.DateUtils.timestampOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GetAccountBalanceController.class)
class GetAccountBalanceControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountBalanceFinder accountBalanceFinder;

    @Test
    void get_account_balance_for_existent_user_with_http_status_200() throws Exception {

        String userId = "2be27975-4d87-413b-991c-ceff0bb960db";
        AccountBalance expectedAccountBalance = new AccountBalance(
                new UserID(userId),
                new TotalBalance(100.00),
                new Currency("EUR"),
                new Date(timestampOf(2021,12,1,1,1,0)));

        when(accountBalanceFinder.findBalance(userId)).thenReturn(Optional.of(expectedAccountBalance));

        ResultMatcher resultMatcher = content().json("{\"balance\":100.0}");
        mockMvc.perform(get("/user/" + userId + "/account-balance/"))
                .andExpect(status().isOk())
                .andExpect(resultMatcher);

    }

    @Test
    void return_404_user_not_found_with_nonexistent_user_id() throws Exception {

        String userId = "2be27975-4d87-413b-991c-ceff0bb960db";

        when(accountBalanceFinder.findBalance(userId)).thenThrow(new UserNotFoundException(userId));

        ResultMatcher resultMatcher = content().json("{\"localizedMessage\": \"User not found with ID: " + userId + "\"}");
        mockMvc.perform(get("/user/" + userId + "/account-balance/"))
                .andExpect(status().isNotFound())
                .andExpect(resultMatcher);

    }
}