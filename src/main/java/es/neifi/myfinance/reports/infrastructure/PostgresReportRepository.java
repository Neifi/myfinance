package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class PostgresReportRepository implements ReportRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private String columns = "reportId,totalExpenses,totalIncomes,totalSavings,isExpense,date";

    public PostgresReportRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
    }

    @Override
    public List<Report> search() {
        return null;
    }

    @Override
    public Optional<Report> findLast() {
        return Optional.empty();
    }

    @Override
    public Optional<Report> findById(String id) {
        return Optional.empty();
    }
}
