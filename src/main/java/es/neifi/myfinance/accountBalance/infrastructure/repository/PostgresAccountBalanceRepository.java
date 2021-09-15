package es.neifi.myfinance.accountBalance.infrastructure.repository;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.AccountBalanceRepository;
import es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class PostgresAccountBalanceRepository implements AccountBalanceRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostgresAccountBalanceRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveAccountBalance(AccountBalance accountBalance) {
        String query = "INSERT INTO account_balance (userId,total_balance,currency,date) VALUES (:userId,:total_balance,:currency,:date)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("userId",accountBalance.userID().value());
        parameterSource.addValue("total_balance",accountBalance.totalBalance().value());
        parameterSource.addValue("currency",accountBalance.currency().value());
        parameterSource.addValue("date", new Timestamp(accountBalance.date().value()));

        namedParameterJdbcTemplate.update(query,parameterSource);
    }

    @Override
    public Optional<AccountBalance> searchAccountBalance(String userId) {
        String query = "SELECT userId,total_balance,currency,date FROM account_balance WHERE userId = :userId";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("userId",userId);
        AccountBalance result = namedParameterJdbcTemplate.queryForObject(query, parameterSource, new ResponseMapper.AccountBalanceRowMapper());
        return Optional.of(result);
    }

    @Override
    public void updateAccountBalance(String userId, Double amount) {
        String query = "UPDATE account_balance SET total_balance=:amount,date=:date WHERE userId = :userId";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("userId",userId);
        parameterSource.addValue("amount",amount);
        parameterSource.addValue("date", Timestamp.from(Instant.now()));

        namedParameterJdbcTemplate.update(query,parameterSource);
    }
}
