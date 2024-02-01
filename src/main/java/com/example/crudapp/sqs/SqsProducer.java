package com.example.crudapp.sqs;

import com.example.crudapp.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqsProducer {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${aws.sqs.queueName}")
    private String queueName;

    public SqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    public void sendMessage(Order order) {
        queueMessagingTemplate.convertAndSend(queueName, order);
    }
}


