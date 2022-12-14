package com.sqs.sqsexample.service;

public interface SQSService {
    void sendMessageToQueue(String message);
}
