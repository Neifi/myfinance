package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper.ReportRowMapper;

public class PostgresReportRepository implements ReportRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private String columns = "reportId,totalExpenses,totalIncomes,totalSavings,isExpense,date";

    public PostgresReportRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String lastSavedId = "";

    @Override
    public void saveReport(Report report) {

        String query = "INSERT INTO report (" + columns + ") VALUES (" +
                ":reportId," +
                ":totalExpenses," +
                ":totalIncomes," +
                ":totalSavings," +
                ":isExpense," +
                ":date" +
                ")";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("reportId", report.getReportId().value());
        parameters.addValue("totalExpenses", report.getTotalExpenses().value());
        parameters.addValue("totalIncomes", report.getTotalIncomes().value());
        parameters.addValue("totalSavings", report.getTotalSavings().value());
        parameters.addValue("isExpense", report.getIsExpense().isTrue());
        parameters.addValue("date", new Timestamp(report.getDate().value()));

        jdbcTemplate.update(query, parameters);
        this.lastSavedId = report.getReportId().value();

    }

    @Override
    public Optional<Report> findById(String reporId) {
        String query = "SELECT " + columns + " FROM report WHERE reportId = :reportId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("reportId", reporId);

        try {
           return Optional.of(jdbcTemplate.queryForObject(query, parameters, new ReportRowMapper()));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }

    @Override
    public Optional<Report> findLast() {
        return findById(lastSavedId);
    }

    @Override
    public List<Report> search() {
        return null;
    }
}
