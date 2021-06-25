package io.lenur.aws.service;

import io.lenur.aws.config.AwsConfig;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@AllArgsConstructor
@Log4j2
public class SqsService {

    @Autowired
    private final SqsClient sqsClient;
    @Autowired
    private final AwsConfig awsConfig;

    public void sendMessage(String payload) {
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(awsConfig.getSqsQueue())
                .messageBody(payload)
                .delaySeconds(10)
                .build());
    }
}
