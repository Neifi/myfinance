package es.neifi.myfinance.shared.Infrastructure;

import es.neifi.myfinance.MyfinanceApplication;
import es.neifi.myfinance.shared.Infrastructure.repository.RepostitoryTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyfinanceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestBase extends RepostitoryTest {

}
