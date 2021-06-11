package es.neifi.myfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"es.neifi.myfinance"})
public class MyfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfinanceApplication.class, args);
	}

}
