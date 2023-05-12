package com.epam.schedulers;

import com.epam.messaging.SnsMessagePublisher;
import com.epam.messaging.SqsMessageConsumer;
import com.epam.messaging.SqsMessageRemover;
import com.epam.notification.EmailNotificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationsScheduledPublisher {

    private final EmailNotificationBuilder emailNotificationBuilder;
    private final SqsMessageConsumer sqsMessageConsumer;
    private final SnsMessagePublisher snsMessagePublisher;
    private final SqsMessageRemover sqsMessageRemover;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void publish() {
        List<Message> messages = sqsMessageConsumer.receiveMessages();
        for (Message message : messages) {
            Long imageId = Long.valueOf(message.body());

            // build message string and url
            String notificationText = emailNotificationBuilder.buildByImageId(imageId);

            boolean isPublished = snsMessagePublisher.publish(notificationText);
            if (isPublished) {
                sqsMessageRemover.deleteMessage(message);
            }
        }
    }
}
