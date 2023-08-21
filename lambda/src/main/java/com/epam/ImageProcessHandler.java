package com.epam;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.epam.messaging.SnsMessagePublisher;
import com.epam.messaging.SqsMessageConsumer;
import com.epam.messaging.SqsMessageRemover;
import com.epam.notification.EmailNotificationBuilder;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;
import java.util.Map;

public class ImageProcessHandler implements RequestHandler<Map<String, String>, String> {

    private static final String SQS_URL = "https://sqs.us-east-1.amazonaws.com/262160507634/t8-uploads-notification-queue";
    private static final String TOPIC_ARN = "arn:aws:sns:us-east-1:262160507634:t8-uploads-notification-topic";
    private static final String REGION = "eu-central-1";
    private static final String API = "API";
    private static final String DETAIL_TYPE_PARAM = "detail-type";
    private static final Integer RECEIVE_MESSAGES_TIMEOUT = 3;
    private static final Integer MAX_NUMBER_OF_MESSAGES = 3;

    private EmailNotificationBuilder emailNotificationBuilder;
    private SqsMessageConsumer sqsMessageConsumer;
    private SqsMessageRemover sqsMessageRemover;
    private SnsMessagePublisher snsMessagePublisher;

    public ImageProcessHandler() {
        SqsClient sqsClient = SqsClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .build();
        sqsMessageConsumer = new SqsMessageConsumer(sqsClient);
        sqsMessageConsumer.setQueueUrl(SQS_URL);

        sqsMessageRemover = new SqsMessageRemover(sqsClient);
        sqsMessageRemover.setQueueUrl(SQS_URL);

        SnsClient snsClient = SnsClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .build();
        snsMessagePublisher = new SnsMessagePublisher(snsClient);
        snsMessagePublisher.setTopicArn(TOPIC_ARN);
    }


    @Override
    public String handleRequest(Map<String, String> input, Context context) {

        LambdaLogger logger = context.getLogger();
        //receive
        List<Message> messages = sqsMessageConsumer.receiveMessages();

        for (Message message : messages) {
            Long imageId = Long.valueOf(message.body());
            // build message string and url
            String notificationText = "publishing message : " + message;

            boolean isPublished = snsMessagePublisher.publish(notificationText);
            if (isPublished) {
//                sqsMessageRemover.deleteMessage(message);
            }
        }

        Map<String, String> env = System.getenv();
        logger.log("env : " + env.toString());


        String response = "200 OK";
        // log execution details

        return response;
    }

}
