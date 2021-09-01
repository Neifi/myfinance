package es.neifi.myfinance.registry.infrastructure.repository;

import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper.RegistryRowMapper;

public class PostgresRegistryRepository implements RegistryRepository {


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PostgresRegistryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String registryFields = "userId,registryId,category,name,cost,currency,date,isExpense ";

    @Override
    public void save(Registry registry) {
        String query = "INSERT INTO registry " +
                "(" + registryFields + ")" +
                "VALUES(" +
                ":userId," +
                ":registryId," +
                ":category," +
                ":name," +
                ":cost," +
                ":currency," +
                ":date," +
                ":isExpense" +
                ")".trim();

        MapSqlParameterSource namedParams = new MapSqlParameterSource();
        namedParams.addValue("registryId", registry.getId().value());
        namedParams.addValue("userId", registry.getUserId().value());
        namedParams.addValue("category", registry.getCategory().value());
        namedParams.addValue("name", registry.getName().value());
        namedParams.addValue("cost", registry.getCost().value());
        namedParams.addValue("currency", registry.getCurrency().value());
        namedParams.addValue("date", new Timestamp(registry.getDate().value()));
        namedParams.addValue("isExpense", registry.isExpense());

        jdbcTemplate.update(query, namedParams);
    }

    @Override
    public Optional<Registry> searchRegistryById(String registryId) {
        String query =
                "SELECT " +
                        registryFields +
                        "FROM " +
                        "registry " +
                        "WHERE " +
                        "registryId = :registryId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("registryId", registryId);

        try {
            return Optional.ofNullable(jdbcTemplate
                    .queryForObject(query, parameters, new RegistryRowMapper()));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Registry> search(String userId) {
        return ResponseMapper.registryListRowMapper(searchSpecificRegistry(userId, true));
    }

    @Override
    public List<Registry> searchIncomes(String userId) {
        return ResponseMapper.registryListRowMapper(searchSpecificRegistry(userId, false));
    }

    private List<Map<String, Object>> searchSpecificRegistry(String userId, boolean isExpense) {
        String query =
                "SELECT " +
                        registryFields +
                        "FROM " +
                        "registry " +
                        "WHERE userId = :userId AND isExpense = :isExpense";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);
        parameters.addValue("isExpense", isExpense);

        List<Map<String, Object>> registriesMap = jdbcTemplate.queryForList(query, parameters);
        return registriesMap;
    }

    @Override
    public List<Registry> searchExpenses(String userId) {
        String query =
                "SELECT " +
                        registryFields +
                        "FROM " +
                        "registry " +
                        "WHERE userId = :userId AND isExpense = true";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);

        List<Map<String, Object>> registriesMap = jdbcTemplate.queryForList(query, parameters);
        return ResponseMapper.registryListRowMapper(registriesMap);
    }

    @Override
    public List<Registry> searchExpenses(String userId, Long initialDate, Long endDate) {
        return ResponseMapper.registryListRowMapper(searchRegistryInDates(userId, initialDate, endDate, true));
    }

    @Override
    public List<Registry> searchIncomes(String userId, Long initialDate, Long endDate) {

        return ResponseMapper.registryListRowMapper(searchRegistryInDates(userId, initialDate, endDate, false));
    }

    private List<Map<String, Object>> searchRegistryInDates(String userId, Long initialDate, Long endDate, boolean isExpense) {
        String query =
                "SELECT " +
                        registryFields +
                        "FROM " +
                        "registry " +
                        "WHERE userId = :userId AND isExpense = :isExpense " +
                        "AND date IN (:initialDate, :endDate)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);
        parameters.addValue("initialDate", new Timestamp(initialDate));
        parameters.addValue("endDate", new Timestamp(endDate));
        parameters.addValue("isExpense", isExpense);

        return jdbcTemplate.queryForList(query, parameters);
    }
}
