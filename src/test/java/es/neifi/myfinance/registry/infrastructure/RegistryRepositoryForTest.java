package es.neifi.myfinance.registry.infrastructure;

import es.neifi.myfinance.registry.domain.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper.RegistryRowMapper;

public class RegistryRepositoryForTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Registry select(String registryId) {
        String query = "SELECT userId,registryId,category,name,cost,currency,date,isExpense " +
                "FROM registry " +
                "WHERE registryId = :registryId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("registryId", registryId);

        return jdbcTemplate
                .queryForObject(query, parameters, new RegistryRowMapper());
    }

}


