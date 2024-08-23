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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public DirectExchange contractorsContractorExchange() {
        return ExchangeBuilder
                .directExchange("contractors_contractor_exchange")
                .durable(true)
                .build();
    }

    @Bean
    public Queue dealsContractorQueue() {
        return QueueBuilder
                .durable("deals_contractor_queue")
                .deadLetterExchange("deals_dead_exchange")
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

}
