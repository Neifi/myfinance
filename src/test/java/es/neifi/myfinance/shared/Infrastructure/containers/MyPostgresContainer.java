package es.neifi.myfinance.shared.Infrastructure.containers;

import org.testcontainers.containers.PostgreSQLContainer;

import static java.lang.String.valueOf;

public class MyPostgresContainer extends PostgreSQLContainer<MyPostgresContainer> {
    public static final String IMAGE_VERSION = "postgres:12.4";
    public static final String DATABASE_NAME = "myfinancedb";
    public static PostgreSQLContainer container;

    public MyPostgresContainer() {
        super(IMAGE_VERSION);
    }


    public static PostgreSQLContainer getInstance() {
        if (container == null) {
            container = new MyPostgresContainer()
                    .withDatabaseName(DATABASE_NAME);
        }

        return container;
    }

    @Override
    public void start() {
        super.start();

        System.setProperty("db_username", container.getUsername());
        System.setProperty("db_password", container.getPassword());
        System.setProperty("db_host", container.getHost());
        System.setProperty("db_port", valueOf(container.getFirstMappedPort()));
        System.setProperty("db_name", container.getDatabaseName());
    }

    @Override
    public void stop() {
    }
}
