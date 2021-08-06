package es.neifi.myfinance.shared.Infrastructure.integration.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootTest
public class DatabaseTestCase {

    @Autowired
    private DataSource dataSource;

    @Test
    void connect_to_db() {
        String sentence = "SELECT version();";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String result = jdbcTemplate.queryForObject(sentence, String.class);

        Assertions.assertTrue(result.startsWith("postgres"));
    }
}
