package io.lenur.aws.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AwsConfig {
    @Value("${aws.endpoint.url:#{null}}")
    private String endpoint;

    @Value("${aws.s3.region:#{null}}")
    private String s3Region;

    @Value("${aws.s3.bucket}")
    private String s3Bucket;

    @Value("${aws.sns.topic-arn}")
    private String snsTopicArn;

    @Value("${aws.sqs.queue}")
    private String sqsQueue;
}
