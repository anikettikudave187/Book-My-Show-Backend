package com.bms.central_api_v1.integrations;

import com.bms.central_api_v1.RequestBody.NotificatonMsgRB;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqInteg {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    NotificatonMsgRB notificatonMsgRB;

    String exchangeName="bms-notification-exchangeString queueName=";

    String routingKey="notification-route";

    public void insertMsgToQueue(NotificatonMsgRB msg){
        rabbitTemplate.convertAndSend(exchangeName,routingKey,msg);
    }

}
