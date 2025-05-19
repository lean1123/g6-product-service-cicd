package ktpm17ctt.g6.product.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;

import java.net.URI;

@Getter
@Configuration
public class AWSConfig {

    @Value("${app.aws.access-key-id}")
    private String accessKeyId;

    @Value("${app.aws.secret-access-key}")
    private String secretAccessKey;

    @Value("${app.aws.region}")
    private String region;

    @Value("${app.aws.s3-bucket-name}")
    private String s3BucketName;

    @Value("${app.aws.cloudfront-url}")
    private String cloudfrontUrl;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKeyId, secretAccessKey)
                        )
                )
                .endpointOverride(URI.create("https://s3.ap-southeast-1.amazonaws.com"))
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
    }
}
