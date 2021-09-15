package es.neifi.myfinance.accountBalance.infrastructure;

import es.neifi.myfinance.accountBalance.application.AccountBalanceFinder;
import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.users.application.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GetAccountBalanceController {

    private final AccountBalanceFinder accountBalanceFinder;

    public GetAccountBalanceController(AccountBalanceFinder accountBalanceFinder) {
        this.accountBalanceFinder = accountBalanceFinder;
    }

    @GetMapping("user/{userId}/account-balance")
    public ResponseEntity<?> getAccountBalance(@PathVariable String userId){

        try {
            Optional<AccountBalance> accountBalance = accountBalanceFinder.findBalance(userId);
            Double balance = accountBalance.get().totalBalance().value();
            String currency = accountBalance.get().currency().value();
            AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse(balance, currency);
            return ResponseEntity.ok(accountBalanceResponse);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserNotFoundException(userId));
        }

    }

    private static class AccountBalanceResponse {
        private final double balance;
        private final String currency;
        public AccountBalanceResponse(double balance,String currency) {
            this.balance = balance;
            this.currency = currency;
        }

        public double getBalance() {
            return balance;
        }

        public String getCurrency() {
            return currency;
        }
    }
}
