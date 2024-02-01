package com.example.crudapp.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
//import org.springframework.messaging.rsocket.service.PayloadArgumentResolver;
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSqs
public class SqsConfig {


    @Value("${aws.region}")
    private String region;


    @Value("${aws.sqs.endpoint}")
    private String sqsEndpoint;

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync());
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsEndpoint, region)) // Adjust the region if necessary
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }


    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory() {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQSAsync());
        return factory;
    }
//
//
    @Bean
    public QueueMessageHandler customQueueMessageHandler() {
        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        factory.setAmazonSqs(amazonSQSAsync());
        QueueMessageHandler handler = factory.createQueueMessageHandler();

        // Add PayloadArgumentResolver to the argument resolvers list
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        argumentResolvers.add(new PayloadArgumentResolver(new MappingJackson2MessageConverter()));
        handler.setArgumentResolvers(argumentResolvers);

        return handler;
    }

}