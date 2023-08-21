package com.epam.messaging;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Component
@Setter
@RequiredArgsConstructor
@Slf4j
public class SnsMessagePublisher {

    @Value("${sns.topic-arn}")
    private String topicArn;

    private final SnsClient snsClient;

    public boolean publish(String message) {
        boolean isPublished = false;
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .topicArn(topicArn)
                    .build();

            PublishResponse response = snsClient.publish(request);
            log.info(response.messageId() + " Message sent. Status is " + response.sdkHttpResponse().statusCode());
            isPublished = response.sdkHttpResponse().isSuccessful();
        } catch (SnsException e) {
           log.error(e.getMessage(), e);
        }

        return isPublished;
    }
}
