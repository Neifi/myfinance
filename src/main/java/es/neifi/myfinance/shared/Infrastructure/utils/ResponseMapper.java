package es.neifi.myfinance.shared.Infrastructure.utils;

import es.neifi.myfinance.accountBalance.domain.AccountBalance;
import es.neifi.myfinance.accountBalance.domain.Amount;
import es.neifi.myfinance.registry.application.searchRegistry.RegistryResponse;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.vo.Category;
import es.neifi.myfinance.registry.domain.vo.Cost;
import es.neifi.myfinance.registry.domain.vo.Currency;
import es.neifi.myfinance.registry.domain.vo.Date;
import es.neifi.myfinance.registry.domain.vo.Name;
import es.neifi.myfinance.registry.domain.vo.RegistryID;

import es.neifi.myfinance.users.domain.Avatar;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
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
                    .userId(registry.userId().value())
                    .id(registry.id().value())
                    .category(registry.category().value())
                    .name(registry.name().value())
                    .cost(registry.cost().value())
                    .currency(registry.currency().value())
                    .date(registry.date().value())
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

    public static final class AccountBalanceRowMapper implements RowMapper<AccountBalance> {

        @Override
        public AccountBalance mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AccountBalance(
                    new UserID(rs.getString("userId")),
                    new Amount(rs.getDouble("total_balance")),
                    new Currency(rs.getString("currency")),
                    new Date(rs.getTimestamp("date").getTime())
            );
        }
    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.createUser(
                    new UserID(rs.getString("userId")),
                    new UserName(rs.getString("username")),
                    new Email(rs.getString("email")),
                    new Avatar(rs.getString("avatar")
                    )
            );
        }
    }

}
