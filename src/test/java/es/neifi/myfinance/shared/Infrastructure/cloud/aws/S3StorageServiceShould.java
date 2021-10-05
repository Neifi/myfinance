package es.neifi.myfinance.shared.Infrastructure.cloud.aws;


import es.neifi.myfinance.shared.domain.UploadContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@SpringBootTest
class S3StorageServiceShould {

    @Autowired
    private S3StorageService cloudFrontCDN;

    @Test
    void upload_file_to_s3() {
        UploadContent content = new UploadContent("src/test/java/es/neifi/myfinance/shared/Infrastructure/cloud/aws/testFile.txt");
        content.withId(UUID.randomUUID().toString()).withName("test").withUploadedOn(Timestamp.from(Instant.now()).toString());
        content.putMetadata("contentType","text/plain");
        cloudFrontCDN.store(content);
    }

}