package com.bms.central_api_v1.configurations;

import com.bms.central_api_v1.RequestBody.NotificatonMsgRB;
import com.bms.central_api_v1.integrations.DBApi;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    String exchangeName="bms-notification-exchange";
    String queueName="notification-queue";
    String routingKey="notification-route";

    @Bean
    public Binding bindQueueWithExchange(DirectExchange exchange, Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public CachingConnectionFactory getConnection(){
        CachingConnectionFactory connection=new CachingConnectionFactory("localhost");
        connection.setUsername("guest");
        connection.setPassword("guest");
        return connection;
    }

    @Bean
    public RabbitTemplate getRabbitTemplate(CachingConnectionFactory connection){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connection);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange getExchange(){
        return new DirectExchange(exchangeName);
    }
    @Bean
    public Queue getMessagingQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public DBApi dbApi(){
        return new DBApi();
    }

    @Bean
    public NotificatonMsgRB notificatonMsgRB(){
        return new NotificatonMsgRB();
    }
}
