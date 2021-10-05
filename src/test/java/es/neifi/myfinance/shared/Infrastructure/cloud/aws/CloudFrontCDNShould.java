package es.neifi.myfinance.shared.Infrastructure.cloud.aws;

import com.amazonaws.SdkClientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CloudFrontCDNShould {

    @Autowired
    private S3StorageService cloudFrontCDN;

    @Test
    void retrieve_existent_file_url() {
        String id = "0c2350c1-5e5d-44fe-b386-84a0a2ae8043";
        cloudFrontCDN.retrieve(id);

    }

    @Test
    void throw_exception_when_file_dont_exist() {
        Exception exception = Assertions.assertThrows(SdkClientException.class, () -> {
            cloudFrontCDN.retrieve("someId");
        });
    }
}