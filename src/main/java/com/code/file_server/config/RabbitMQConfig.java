package com.code.file_server.config;

import org.springframework.amqp.core.*;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME="messageBroker";
    public static final String QUEUE_EXCHANGE="messagingExchange";
    public static final String QUEUE_KEY="messagingKey";

    @Bean
    Queue queue(){
        return new Queue(QUEUE_NAME,true);
    }
    @Bean
    DirectExchange exchange(){
        return new DirectExchange(QUEUE_EXCHANGE);
    }
    @Bean
    Binding binding(Queue queue,DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_KEY);
    }
    @Bean
    public MessageConverter jsonMessageConvertor(){
        return new JacksonJsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory factory){
        final RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(jsonMessageConvertor());
        return template;
    }
}
