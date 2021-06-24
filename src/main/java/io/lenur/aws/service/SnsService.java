package io.lenur.aws.service;

import io.lenur.aws.config.AwsConfig;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;

@Service
@AllArgsConstructor
@Log4j2
public class SnsService {

    @Autowired
    private final SnsClient snsClient;
    @Autowired
    private final AwsConfig awsConfig;

    public void subscribe(String email) {
        var request = SubscribeRequest.builder()
                .protocol("email")
                .endpoint(email)
                .returnSubscriptionArn(true)
                .topicArn(awsConfig.getSnsTopicArn())
                .build();
        snsClient.subscribe(request);
    }
}
