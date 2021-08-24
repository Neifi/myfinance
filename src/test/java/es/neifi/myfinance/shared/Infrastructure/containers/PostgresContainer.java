package es.neifi.myfinance.shared.Infrastructure.containers;

import org.testcontainers.containers.PostgreSQLContainer;

import static java.lang.String.valueOf;

public class PostgresContainer extends PostgreSQLContainer<PostgresContainer> {
    public static final String IMAGE_VERSION = "postgres:12.4";
    public static final String DATABASE_NAME = "test";
    public static PostgreSQLContainer container;

    public PostgresContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgreSQLContainer getInstance() {
        if (container == null) {
            container = new PostgresContainer()
                    .withDatabaseName(DATABASE_NAME).withConnectTimeoutSeconds(100000);
        }

        return container;
    }

    @Override
    public void start(){
        super.start();
        System.setProperty("db_host",container.getJdbcUrl());
        System.setProperty("db_username",container.getUsername());
        System.setProperty("db_password",container.getPassword());
        System.setProperty("db_host",container.getHost());
        System.setProperty("db_port", valueOf(container.getFirstMappedPort()));
        System.setProperty("db_name",container.getDatabaseName());
    }

    @Override
    public void stop(){
        super.stop();
    }
}
