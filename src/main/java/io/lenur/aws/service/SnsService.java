package io.lenur.aws.service;

import io.lenur.aws.config.AwsConfig;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsByTopicRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.Subscription;
import software.amazon.awssdk.services.sns.model.UnsubscribeRequest;

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

    public void unsubscribe(String email) {
        var request = UnsubscribeRequest.builder()
                .subscriptionArn(getSubscriptionArn(email))
                .build();
        snsClient.unsubscribe(request);
    }

    private String getSubscriptionArn(String email) {
        var request = ListSubscriptionsByTopicRequest.builder()
                .topicArn(awsConfig.getSnsTopicArn())
                .build();
        return snsClient.listSubscriptionsByTopic(request)
                .subscriptions()
                .stream()
                .filter(x -> x.protocol().equals("email") && x.endpoint().equals(email))
                .map(Subscription::subscriptionArn)
                .findFirst()
                .orElseThrow(() -> {
                    var msg = String.format("The email %s cannot be unsubscribed", email);
                    return new IllegalArgumentException(msg);
                });
    }
}
