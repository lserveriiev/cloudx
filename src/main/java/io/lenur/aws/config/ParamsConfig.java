package io.lenur.aws.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ParamsConfig {
    @Value("${s3.bucket}")
    private String s3Bucket;
}
