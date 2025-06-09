package com.bms.central_api_v1.Service;

import com.bms.central_api_v1.RequestBody.AcceptTheaterRB;
import com.bms.central_api_v1.RequestBody.CreateTheaterNotificationRB;
import com.bms.central_api_v1.RequestBody.CreateTheaterRB;
import com.bms.central_api_v1.RequestBody.NotificatonMsgRB;
import com.bms.central_api_v1.exceptions.UnAuthorizedException;
import com.bms.central_api_v1.integrations.DBApi;
import com.bms.central_api_v1.integrations.RabbitMqInteg;
import com.bms.central_api_v1.models.AppUser;
import com.bms.central_api_v1.models.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TheaterService {
    @Autowired
    DBApi dbApi;

    @Autowired
    UserService userService;

    @Autowired
    RabbitMqInteg rabbitMqInteg;

    public Theater getTheaterById(UUID theaterId){
        return dbApi.callGetTheaterByIdEndpoint(theaterId);
    }

    public Theater raiseCreateTheaterRequest(CreateTheaterRB theaterRB, UUID theaterOwnerId,String authorization)throws UnAuthorizedException {
        boolean isTheaterOwner=userService.isTheaterOwner(theaterOwnerId);
        if(!isTheaterOwner){
            throw new UnAuthorizedException(String.format("this user id %s not have access to create theater",theaterOwnerId.toString()));
        }
        AppUser owner=userService.getUserById(theaterOwnerId);
        Theater theater=dbApi.callCreateTheaterEndpoint(theaterRB,owner);


        List<AppUser> allAdmins=userService.getAllAdmins();
        this.notifyAllAdminsRegardingNewTheaterRequest(allAdmins,theater);
        return theater;
    }

    public void acceptTheaterRequest(UUID adminId,UUID theaterId){
        Theater theater=dbApi.callGetTheaterById(theaterId);
        theater.setStatus("ACTIVE");
        theater=dbApi.callUpdateTheaterEndpoint(theater);
        AppUser admin=userService.getUserById(adminId);
        this.notifyTheaterOwnerRegardingTheaterAcceptance(theater,admin);

    }

    public void notifyAllAdminsRegardingNewTheaterRequest(List<AppUser> admins, Theater theater){
        for(AppUser admin:admins){
            CreateTheaterNotificationRB theaterNotificationRB=new CreateTheaterNotificationRB();
            theaterNotificationRB.setTheater(theater);
            theaterNotificationRB.setAdmin(admin);

            NotificatonMsgRB message=new NotificatonMsgRB();
            message.setMsgType("Create-Theater");
            message.setPayload(theaterNotificationRB);

            rabbitMqInteg.insertMsgToQueue(message);
        }
    }

    public void notifyTheaterOwnerRegardingTheaterAcceptance(Theater theater, AppUser admin){
        AcceptTheaterRB theaterRB=new AcceptTheaterRB();
        theaterRB.setTheater(theater);
        theaterRB.setAdmin(admin);

        NotificatonMsgRB message=new NotificatonMsgRB();
        message.setMsgType("THEATER_ACCEPTANCE");
        message.setPayload(theaterRB);

        rabbitMqInteg.insertMsgToQueue(message);
    }


}
