package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.shared.Infrastructure.utils.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class ReportRepositortForTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Report select(String reportId) {
        String query = "SELECT reportId,userId,totalExpenses,totalIncomes,totalSavings,isExpense,date " +
                "FROM report " +
                "WHERE reportId = :reportId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("reportId", reportId);

        return jdbcTemplate
                .queryForObject(query, parameters, new ResponseMapper.ReportRowMapper());
    }
}
