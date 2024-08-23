package ru.luttsev.contractors.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.main-borrower-exchange}")
    private String mainBorrowerExchangeName;

    @Value("${rabbitmq.main-borrower-queue}")
    private String mainBorrowerQueueName;

    @Value("${rabbitmq.contractor-exchange}")
    private String contractorExchangeName;

    @Value("${rabbitmq.contractor-queue}")
    private String contractorQueueName;

    @Value("${rabbitmq.dead-exchange}")
    private String deadExchangeName;

    @Bean
    public DirectExchange contractorsContractorExchange() {
        return ExchangeBuilder
                .directExchange(contractorExchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Queue dealsContractorQueue() {
        return QueueBuilder
                .durable(contractorQueueName)
                .deadLetterExchange(deadExchangeName)
                .build();
    }

    @Bean
    public Binding exchangeToQueueBinding() {
        return BindingBuilder
                .bind(dealsContractorQueue())
                .to(contractorsContractorExchange())
                .withQueueName();
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public DirectExchange mainBorrowerExchange() {
        return ExchangeBuilder
                .directExchange(mainBorrowerExchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Queue mainBorrowerQueue() {
        return QueueBuilder
                .durable(mainBorrowerQueueName)
                .build();
    }

    @Bean
    public Binding mainBorrowerBinding() {
        return BindingBuilder
                .bind(mainBorrowerQueue())
                .to(mainBorrowerExchange())
                .withQueueName();
    }

}
