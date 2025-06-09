package com.bms.notification_v1_api.controller;

import com.bms.notification_v1_api.requestBody.AcceptCreateTheaterRB;
import com.bms.notification_v1_api.requestBody.NotificationMessage;
import com.bms.notification_v1_api.requestBody.TheaterRB;
import com.bms.notification_v1_api.service.TheaterMailService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

@RestController
public class RbbitMqController {

    @Autowired
    ModelMapper mapper;

    @Autowired
    TheaterMailService theaterMailService;

    @RabbitListener(queues = "bms-notification queue")
    public void consumeMessage(@Payload NotificationMessage notificationMessage) throws Exception{
        String messageType=notificationMessage.getMessageType();
        if(messageType.equals("create_theater")){
            Object payload=notificationMessage.getPayload();
            TheaterRB theaterRB=mapper.map(payload,TheaterRB.class);
            theaterMailService.notifyAdminForCreateTheaterRequest(theaterRB);
        }else if(messageType.equals("create_user")){
        }else if(messageType.equals("notify_user_bill")){

        }else if(messageType.equals("THEATER_ACCEPTANCE")){
            Object payload=notificationMessage.getPayload();
            AcceptCreateTheaterRB acceptCreateTheaterRB=mapper.map(payload,AcceptCreateTheaterRB.class);
            theaterMailService.sendTheaterRequestAcceptanceMail(acceptCreateTheaterRB);
        }
    }
}
