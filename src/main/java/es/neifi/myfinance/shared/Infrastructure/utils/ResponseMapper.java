package es.neifi.myfinance.shared.Infrastructure.utils;

import es.neifi.myfinance.registry.application.searchRegistry.RegistryResponse;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;
import es.neifi.myfinance.reports.domain.IsExpense;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import es.neifi.myfinance.reports.domain.TotalExpenses;
import es.neifi.myfinance.reports.domain.TotalIncomes;
import es.neifi.myfinance.reports.domain.TotalSavings;
import es.neifi.myfinance.users.domain.UserID;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseMapper {

    public static List<Registry> mapToRegistryResponse(List<RegistryResponse> registryData, List<Registry> expens) {

        for (Registry registry : expens) {
            registryData.add(RegistryResponse.builder()
                    .userId(registry.getUserId().value())
                    .id(registry.getId().value())
                    .category(registry.getCategory().value())
                    .name(registry.getName().value())
                    .cost(registry.cost())
                    .currency(registry.getCurrency().getValue())
                    .date(registry.getDate().value())
                    .isExpense(registry.isExpense())
                    .build());
        }
        return expens;
    }

    public static List<Registry> registryListRowMapper(List<Map<String, Object>> registryList) {

        ArrayList<Registry> registries = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : registryList) {
            BigDecimal cost = (BigDecimal) stringObjectMap.get("cost");
            Timestamp date = (Timestamp) stringObjectMap.get("date");
            Registry registry = new Registry(
                    new UserID((String) stringObjectMap.get("userId")),
                    new RegistryID((String) stringObjectMap.get("registryId")),
                    new Category((String) stringObjectMap.get("category")),
                    new Name((String) stringObjectMap.get("name")),
                    new Cost(cost.doubleValue()),
                    new Currency((String) stringObjectMap.get("currency")),
                    new Date(date.getTime()),
                    (Boolean) stringObjectMap.get("isExpense")
            );
            registries.add(registry);
        }

        return registries;
    }

    public static final class RegistryRowMapper implements RowMapper<Registry> {

        @Override
        public Registry mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Registry(
                    new UserID(rs.getString("userId")),
                    new RegistryID(rs.getString("registryId")),
                    new Category(rs.getString("category")),
                    new Name(rs.getString("name")),
                    new Cost(rs.getDouble("cost")),
                    new Currency(rs.getString("currency")),
                    new Date(rs.getTimestamp("date").getTime()),
                    rs.getBoolean("isExpense")
            );
        }
    }

    public static class ReportRowMapper implements RowMapper<Report> {
        @Override
        public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
            return  Report.create(
                    new ReportID(rs.getString("reportId")),
                    new UserID(rs.getString("userId")),
                    new TotalExpenses(rs.getDouble("totalExpenses")),
                    new TotalIncomes(rs.getDouble("totalIncomes")),
                    new TotalSavings(rs.getDouble("totalSavings")),
                    new IsExpense(rs.getBoolean("isExpense")),
                    new Date(rs.getTimestamp("date").getTime())
                    );
        }
    }

    public static List<Report> reportListRowMapper(List<Map<String, Object>> reportList) {

        ArrayList<Report> reports = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : reportList) {
            Timestamp date = (Timestamp) stringObjectMap.get("date");
            BigDecimal totalExpenses = (BigDecimal) stringObjectMap.get("totalExpenses");
            BigDecimal totalIncomes = (BigDecimal) stringObjectMap.get("totalIncomes");
            BigDecimal totalSavings = (BigDecimal) stringObjectMap.get("totalSavings");

            Report report = Report.create(
                    new ReportID((String) stringObjectMap.get("reportId")),
                    new UserID((String) stringObjectMap.get("userId")),
                    new TotalExpenses(totalExpenses.doubleValue()),
                    new TotalIncomes(totalIncomes.doubleValue()),
                    new TotalSavings(totalSavings.doubleValue()),
                    new IsExpense((Boolean) stringObjectMap.get("isExpense")),
                    new Date(date.getTime())
            );
            reports.add(report);
        }

        return reports;
    }
}
