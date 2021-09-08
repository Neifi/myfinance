package es.neifi.myfinance.shared.Infrastructure.repository;

import es.neifi.myfinance.shared.Infrastructure.containers.ContainersEnvironment;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;


public class RepostitoryTest extends ContainersEnvironment {
    @Autowired
    private Flyway flyway;

    @Before
    public void init(){
        flyway.clean();
        flyway.migrate();
    }
}
