package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;
import es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper.ReportRowMapper;

@Transactional
public class PostgresReportRepository implements ReportRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private String columns = "reportId,userId,totalExpenses,totalIncomes,totalSavings,isExpense,date";

    public PostgresReportRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void saveReport(Report report) {

        String query = "INSERT INTO report (" + columns + ") VALUES (" +
                ":reportId," +
                ":userId," +
                ":totalExpenses," +
                ":totalIncomes," +
                ":totalSavings," +
                ":isExpense," +
                ":date" +
                ")";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("reportId", report.reportId().value());
        parameters.addValue("userId", report.userId().value());
        parameters.addValue("totalExpenses", report.totalExpenses().value());
        parameters.addValue("totalIncomes", report.totalIncomes().value());
        parameters.addValue("totalSavings", report.totalSavings().value());
        parameters.addValue("isExpense", report.isExpense().value());
        parameters.addValue("date", new Timestamp(report.date().value()));

        jdbcTemplate.update(query, parameters);
    }

    @Override
    public Optional<Report> findById(String reporId) {
        String query = "SELECT " + columns + " FROM report WHERE reportId = :reportId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("reportId", reporId);

        try {
            return Optional.of(jdbcTemplate.queryForObject(query, parameters, new ReportRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Report> findLast(String userId) {
        String query = "SELECT reportId FROM report WHERE userId = :userId ORDER BY date DESC LIMIT 1";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);

        try {
            String lastReportId = jdbcTemplate.queryForObject(query, parameters, String.class);
            return findById(lastReportId);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Report> search(String userId) {
        String query = "SELECT " + columns + " FROM report WHERE userId = :userId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);

        return ResponseMapper.reportListRowMapper(jdbcTemplate.queryForList(query, parameters));
    }
}
