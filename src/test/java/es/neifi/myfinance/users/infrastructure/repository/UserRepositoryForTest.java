package es.neifi.myfinance.users.infrastructure.repository;

import es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper;
import es.neifi.myfinance.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class UserRepositoryForTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public User select(String userId) {
        String query = "SELECT userId,username,email " +
                "FROM users " +
                "WHERE userId = :userId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);

        return jdbcTemplate
                .queryForObject(query, parameters, new ResponseMapper.UserRowMapper());
    }

}
