package es.neifi.myfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"es.neifi.myfinance","es.neifi.myfinance.registry.application","es.neifi.myfinance.users.domain"})
public class MyfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfinanceApplication.class, args);
	}

}
