package com.epam.messaging;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SqsException;

@Component
@RequiredArgsConstructor
@Setter
@Slf4j
public class SqsMessageRemover {
    @Value("${sqs.queueUrl}")
    private String queueUrl;

    private final SqsClient sqsClient;

    public void deleteMessage(Message message) {
        log.info("deleting message with body: " + message.body());

        try {
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();

            DeleteMessageResponse response = sqsClient.deleteMessage(deleteMessageRequest);
            log.info("Message is deleted. Status is " + response.sdkHttpResponse().statusCode());
        } catch (SqsException e) {
            log.error(e.getMessage(), e);
        }
    }
}
