package es.neifi.myfinance.shared.Infrastructure.cloud.aws;

import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudfront.AmazonCloudFront;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClientBuilder;
import com.amazonaws.services.cloudfront.model.GetDistributionRequest;
import org.springframework.beans.factory.annotation.Value;

public class CloudFrontCDN extends S3StorageService {

    private final AmazonCloudFront amazonCloudFrontClient = AmazonCloudFrontClientBuilder
            .standard().withRegion(Regions.EU_WEST_3).build();

    @Value("${aws.cloudfront.distribution-id}")
    private String distributionID;

    @Override
    public String retrieve(String id) {
        exist(id, bucketName);

        String domainName = amazonCloudFrontClient
                .getDistribution(new GetDistributionRequest(distributionID))
                .getDistribution()
                .getDomainName();

        return createUrl(id, domainName);
    }

    private void exist(String id, String bucketName) {
        try {
            s3Client.getObject(bucketName,id);
        } catch (SdkClientException e) {
            throw new SdkClientException(e.getMessage());
        }
    }

    private String createUrl(String id, String domainName) {

        return "https://" +
                domainName +
                "/" +
                id;
    }
}
