package io.lenur.aws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class AppConfig {
    @Bean
    public S3Client s3Client(AwsConfig awsConfig)
            throws URISyntaxException {
        return S3Client.builder()
                .endpointOverride(new URI(awsConfig.getEndpoint()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(awsConfig.getS3Region()))
                .build();
    }

    @Bean
    public SnsClient buildSnsClient(AwsConfig awsConfig)
            throws URISyntaxException {
        return SnsClient.builder()
                .endpointOverride(new URI(awsConfig.getEndpoint()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(awsConfig.getS3Region()))
                .build();
    }
}
