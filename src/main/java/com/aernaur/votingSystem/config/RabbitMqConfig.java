package com.aernaur.votingSystem.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.queue.vote.register}")
    private String registerVoteQueue;

    @Value("${rabbitmq.queue.vote.count}")
    private String countVotesQueue;

    @Value("${rabbitmq.queue.vote.decrypt}")
    private String decryptVoteQueue;

    @Value("${rabbitmq.exchange.name}")
    private String topicExchangeName;

    @Value("${rabbitmq.routing.vote.register}")
    private String registerVoteRoutingKey;

    @Value("${rabbitmq.routing.vote.count}")
    private String countVotesRoutingKey;

    @Value("${rabbitmq.routing.vote.decrypt}")
    private String decryptVotesRoutingKey;

    @Bean
    public Queue registerVoteQueue() {
        return new Queue(registerVoteQueue, true);
    }

    @Bean
    public Queue countVotesQueue() {
        return new Queue(countVotesQueue, true);
    }

    @Bean
    public Queue decryptVoteQueue() {
        return new Queue(decryptVoteQueue, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    public Binding registerVoteBinding() {
        return BindingBuilder.bind(registerVoteQueue()).to(exchange()).with(registerVoteRoutingKey);
    }

    @Bean
    public Binding countVotesBinding() {
        return BindingBuilder.bind(countVotesQueue()).to(exchange()).with(countVotesRoutingKey);
    }

    @Bean
    public Binding decryptVotesRoutingKey() {
        return BindingBuilder.bind(decryptVoteQueue()).to(exchange()).with(decryptVotesRoutingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
