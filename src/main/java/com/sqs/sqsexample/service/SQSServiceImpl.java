package com.sqs.sqsexample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class SQSServiceImpl implements SQSService{

    @Value("${sqs.outbound.uri}")
    private String sqsUri;

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    @Override
    public void sendMessageToQueue(String message) {
        try {
            Map<String, Object> headers = new HashMap<>();
            UUID uuid = UUID.randomUUID();
            headers.put(SqsMessageHeaders.SQS_GROUP_ID_HEADER, uuid.toString());
            headers.put(SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER, uuid.toString());
            log.info("Publishing event  :  {} :  {}", message, uuid);
            messagingTemplate.convertAndSend(sqsUri, message, headers);
        } catch (Exception ex) {
            log.error("Error  :  {}", ex.getMessage());
        }
    }

    @SqsListener(value = "${sqs.inbound.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessageFromQueue(final String payload, @Headers final Map<String, Object> header) {
        log.info("Received message from fifo :  {}  : {}", payload, header.get("MessageGroupId"));
    }

    @SqsListener(value = "${sqs.inbound.error-queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessageFromDLQueue(final String payload, @Headers final Map<String, Object> header) {
        log.info("Received message from DLQ  :  {}  : {}", payload, header.get("MessageGroupId"));
    }


}
