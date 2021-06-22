package io.lenur.aws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class AppConfig {
    @Bean
    public S3Client s3Client(S3Config s3Config)
            throws URISyntaxException {
        return S3Client.builder()
                .endpointOverride(new URI(s3Config.getEndpoint()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(s3Config.getRegion()))
                .build();
    }
}
