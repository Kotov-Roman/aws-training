package com.epam.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsMessageProducer {

    @Value("${sqs.queueUrl}")
    private String queueUrl;

    private final SqsClient sqsClient;

    public void sendMessage(String message) {

        try {
            SendMessageRequest messageRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(message)
                    .delaySeconds(1) //optional
                    .build();

            SendMessageResponse response = sqsClient.sendMessage(messageRequest);
            log.info(response.messageId() + " Message sent. Status is " + response.sdkHttpResponse().statusCode());
        } catch (SqsException e) {
            log.error(e.getMessage(), e);
        }
    }
}
