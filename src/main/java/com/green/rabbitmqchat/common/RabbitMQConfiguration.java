package com.green.rabbitmqchat.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final RabbitMQProperties properties;


    /**
     * 1. Exchange 구성합니다.
     *
     * @return TopicExchange
     */
    //Exchange 등록
    @Bean
    public TopicExchange exchange(){ return new TopicExchange(properties.getChatExchangeName()); }

    /**
     * 2. 큐를 구성합니다.
     * durable: true는 rabbitmq가 재실행되어도 큐가 소멸되지 않는다
     * @return Queue
     */
    @Bean
    Queue queue() {
        return new Queue(properties.getChatQueueName(), false);
    }


    /**
     * 3. Queue와 Exchange를 바인딩합니다.
     *     *
     * @return Binding
     */
    @Bean
    Binding binding() {


        Binding binding = BindingBuilder.bind(queue()).to(exchange()).with(properties.getChatRoutingKey());


        return binding;
    }


    /**
     * 4. RabbitMQ와의 연결을 위한 ConnectionFactory을 구성합니다.
     * Application.properties의 RabbitMQ의 사용자 정보를 가져와서 RabbitMQ와의 연결에 필요한 ConnectionFactory를 구성합니다.
     *
     * @return ConnectionFactory
     */
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(properties.getHost());
        connectionFactory.setVirtualHost(properties.getVirtualHost());
        connectionFactory.setPort(properties.getPort());
        connectionFactory.setUsername(properties.getUsername());
        connectionFactory.setPassword(properties.getPassword());
        return connectionFactory;
    }

    /**
     * 5. 메시지를 전송하고 수신하기 위한 JSON 타입으로 메시지를 변경합니다.
     * Jackson2JsonMessageConverter를 사용하여 메시지 변환을 수행합니다. JSON 형식으로 메시지를 전송하고 수신할 수 있습니다
     *
     * @return
     */
    @Bean
    MessageConverter messageConverter() {
        //LocalDateTime serializable을 위해
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.registerModule(dateTimeModule());

        return new Jackson2JsonMessageConverter(objectMapper);
    }


    /**
     * 6. 구성한 ConnectionFactory, MessageConverter를 통해 템플릿을 구성합니다.
     *
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setRoutingKey(properties.getChatRoutingKey());
        return rabbitTemplate;
    }

    @Bean
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    @Bean
    public Module dateTimeModule(){
        return new JavaTimeModule();
    }
}
