package es.neifi.myfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "es.neifi.myfinance",
        "es.neifi.myfinance.registry.application",
        "es.neifi.myfinance.registry.application.searchRegistry",
        "es.neifi.myfinance.registry.infrastructure",
        "es.neifi.myfinance.accountBalance.infrastructure",
        "es.neifi.myfinance.accountBalance.domain",
        "es.neifi.myfinance.accountBalance.application",
        "es.neifi.myfinance.users.domain",
        "es.neifi.myfinance.shared.Infrastructure"
})
public class MyfinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyfinanceApplication.class, args);
    }

}
