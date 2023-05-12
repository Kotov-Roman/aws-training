package com.epam.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;
import software.amazon.awssdk.services.sns.model.UnsubscribeRequest;
import software.amazon.awssdk.services.sns.model.UnsubscribeResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class SnsSubscriptionsService {

    @Value("${sns.topic-arn}")
    private String topicArn;

    private final SnsClient snsClient;


    public String subscribe(String email) {

        try {
            SubscribeRequest request = SubscribeRequest.builder()
                    .protocol("email")
                    .endpoint(email)
                    .returnSubscriptionArn(true)
                    .topicArn(topicArn)
                    .build();

            SubscribeResponse response = snsClient.subscribe(request);
            String subscriptionArn = response.subscriptionArn();
            log.info("Subscription ARN: " + subscriptionArn
                    + "\n\n Status is " + response.sdkHttpResponse().statusCode());
            return subscriptionArn;

        } catch (SnsException e) {
            log.error(e.getMessage(), e);
            return "smth went wrong";
        }
    }

    public boolean unsubscribe(String subscriptionArn) {

        try {
            UnsubscribeRequest request = UnsubscribeRequest.builder()
                    .subscriptionArn(subscriptionArn)
                    .build();

            UnsubscribeResponse result = snsClient.unsubscribe(request);
            log.info("Status was " + result.sdkHttpResponse().statusCode()
                    + "\n\nSubscription was removed for " + request.subscriptionArn());
            return true;


        } catch (SnsException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
