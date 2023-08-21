package com.epam.messaging;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.util.Collections;
import java.util.List;

@Component
@Setter
@RequiredArgsConstructor
@Slf4j
public class SqsMessageConsumer {

    @Value("${sqs.queueUrl}")
    private String queueUrl;

    private final SqsClient sqsClient;


    public List<Message> receiveMessages() {
        try {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(5)
                    .build();
            List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
            log.info(messages.toString());
            return messages;
        } catch (SqsException e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }
}
