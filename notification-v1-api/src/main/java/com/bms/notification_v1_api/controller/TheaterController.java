package com.bms.notification_v1_api.controller;

import com.bms.notification_v1_api.requestBody.TheaterRB;
import com.bms.notification_v1_api.service.TheaterMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notify/theater")
public class TheaterController {

    @Autowired
    TheaterMailService mailService;

    @PutMapping("/request")
    public void notifyAdminForCreateTheaterRequest(@RequestBody TheaterRB theaterRB)throws MessagingException {
        mailService.notifyAdminForCreateTheaterRequest(theaterRB);
    }
}
