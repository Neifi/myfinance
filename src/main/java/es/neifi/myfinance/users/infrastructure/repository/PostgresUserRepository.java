package es.neifi.myfinance.users.infrastructure.repository;

import es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Optional;

public class PostgresUserRepository implements UserRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public PostgresUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        String query = "INSERT INTO users (userId,username,email,avatar) VALUES (" +
                ":userId, " +
                ":username, " +
                ":email," +
                ":avatar"+
                ")";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId",user.id().value());
        mapSqlParameterSource.addValue("username",user.username().value());
        mapSqlParameterSource.addValue("email",user.email().value());
        mapSqlParameterSource.addValue("avatar",user.avatar().value());

        jdbcTemplate.update(query,mapSqlParameterSource);
    }

    @Override
    public Optional<User> searchById(String userId) {

        String query = "SELECT userId,username,email,avatar " +
                "FROM users " +
                "WHERE userId = :userId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);
        try {
            return Optional.ofNullable(jdbcTemplate
                    .queryForObject(query, parameters, new ResponseMapper.UserRowMapper()));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
