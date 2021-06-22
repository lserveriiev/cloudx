package io.lenur.aws.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class S3Config {
    @Value("${s3.endpoint.url:#{null}}")
    private String s3Endpoint;

    @Value("${s3.region:#{null}}")
    private String region;

    @Value("${s3.bucket}")
    private String s3Bucket;
}
