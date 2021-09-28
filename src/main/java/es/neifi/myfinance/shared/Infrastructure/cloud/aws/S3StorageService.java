package es.neifi.myfinance.shared.Infrastructure.cloud.aws;

import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import es.neifi.myfinance.shared.Infrastructure.cloud.CloudStorageService;
import es.neifi.myfinance.shared.domain.UploadContent;
import org.springframework.beans.factory.annotation.Value;

public class S3StorageService implements CloudStorageService {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final AmazonS3 s3Client = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.EU_WEST_3)
            .build();

    @Override
    public void store(UploadContent uploadContent) {
        try {

            PutObjectRequest request = new PutObjectRequest(bucketName, uploadContent.id(), uploadContent);
            setMetadata(request, uploadContent);

            s3Client.putObject(request);

        } catch (SdkClientException e) {

            e.printStackTrace();
        }
    }

    private void setMetadata(PutObjectRequest request, UploadContent uploadContent) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        String contentType = (String) uploadContent.getMetadata().get("contentType");
        objectMetadata.setContentType(contentType);

        uploadContent.getMetadata()
                .forEach((key, value) -> objectMetadata.addUserMetadata(key, (String) value));

        request.setMetadata(objectMetadata);
    }

}
