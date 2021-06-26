package io.lenur.aws.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class AppConfig {
    @Bean
    public S3Client s3Client(AwsConfig awsConfig)
            throws URISyntaxException {
        var clientBuilder =  S3Client.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(awsConfig.getS3Region()));

        if (awsConfig.getEndpoint() != null) {
            clientBuilder.endpointOverride(new URI(awsConfig.getEndpoint()));
        }

        return clientBuilder.build();

    }

    @Bean
    public SnsClient buildSnsClient(AwsConfig awsConfig)
            throws URISyntaxException {
        var clientBuilder =  SnsClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(awsConfig.getS3Region()));

        if (awsConfig.getEndpoint() != null) {
            clientBuilder.endpointOverride(new URI(awsConfig.getEndpoint()));
        }

        return clientBuilder.build();
    }

    @Bean
    public SqsClient buildSqsClient(AwsConfig awsConfig)
            throws URISyntaxException {
        var clientBuilder =  SqsClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(awsConfig.getS3Region()));

        if (awsConfig.getEndpoint() != null) {
            clientBuilder.endpointOverride(new URI(awsConfig.getEndpoint()));
        }

        return clientBuilder.build();
    }

    @Bean
    public LambdaClient buildLambdaClient(AwsConfig awsConfig)
            throws URISyntaxException {
        var clientBuilder =  LambdaClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(awsConfig.getS3Region()));

        if (awsConfig.getEndpoint() != null) {
            clientBuilder.endpointOverride(new URI(awsConfig.getEndpoint()));
        }

        return clientBuilder.build();
    }

    @Bean
    public Gson buildGson() {
        return new GsonBuilder()
                .setDateFormat("HH:mm:ss")
                .create();
    }
}
