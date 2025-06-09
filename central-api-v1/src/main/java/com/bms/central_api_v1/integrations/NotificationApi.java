package com.bms.central_api_v1.integrations;

import com.bms.central_api_v1.RequestBody.CreateTheaterNotificationRB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class NotificationApi extends RestApi{

    @Value("${notification.api.base}")
    String baseUrl;

    public void callNotifyAdminForTheaterRequestEndpoint(CreateTheaterNotificationRB notificationRB){
        String endpoint= "/theater/request";
        this.makePutCall(baseUrl,endpoint,notificationRB,new HashMap<>());
    }
}
