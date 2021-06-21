package io.lenur.aws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class AppConfig {
    @Bean
    public S3Client s3Client(@Value("${s3.endpoint.url:#{null}}") String s3Endpoint,
                             @Value("${s3.region:#{null}}") String region)
            throws URISyntaxException {
        return S3Client.builder()
                .endpointOverride(new URI(s3Endpoint))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(region))
                .build();
    }
}
