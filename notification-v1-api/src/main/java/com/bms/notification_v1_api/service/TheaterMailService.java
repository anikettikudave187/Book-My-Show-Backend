package com.bms.notification_v1_api.service;

import com.bms.notification_v1_api.models.AppUser;
import com.bms.notification_v1_api.models.Theater;
import com.bms.notification_v1_api.requestBody.AcceptCreateTheaterRB;
import com.bms.notification_v1_api.requestBody.TheaterRB;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;

@Service
public class TheaterMailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${central.api.base}")
    String centralApiBaseUrl;

    public Context createContext(HashMap<String,String>map)throws MessagingException {
        Context context=new Context();
        for(String key:map.keySet()){
            context.setVariable(key,map.get(key));
        }
        return context;
    }

    public void notifyAdminForCreateTheaterRequest(TheaterRB theaterRB)throws MessagingException{
        Theater theater=theaterRB.getTheater();
        AppUser admin=theaterRB.getAdmin();
        String token=theaterRB.getToken();

        Context context=new Context();
        context.setVariable("adminName", theaterRB.getAdmin().getName());
        context.setVariable("theaterName", theaterRB.getTheater().getName());
        context.setVariable("address", theaterRB.getTheater().getAddress());
        context.setVariable("state", theaterRB.getTheater().getState());
        context.setVariable("pincode", theaterRB.getTheater().getPinCode());
        context.setVariable("ownerName", theaterRB.getTheater().getOwner().getName());
        context.setVariable("ownerEmail", theaterRB.getTheater().getOwner().getEmail());

        String acceptEndpoint=centralApiBaseUrl+"/theater/approve/"+theater.getId()+"/"+admin.getId()+"/"+token;
        context.setVariable("acceptEndpoint",acceptEndpoint);
        String htmlEmail=templateEngine.process("theaterRequest",context);

        //create memeMessage for the mail body
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message);
        helper.setSubject("A new theater has registered and is awaiting your approval.");
        helper.setTo(theaterRB.getAdmin().getEmail());
        helper.setText(htmlEmail,true);
        javaMailSender.send(message);
    }

    public void sendTheaterRequestAcceptanceMail(AcceptCreateTheaterRB acceptCreateTheaterRB)throws Exception{
        Theater theater=acceptCreateTheaterRB.getTheater();
        AppUser admin=acceptCreateTheaterRB.getAdmin();
        AppUser owner=acceptCreateTheaterRB.getTheater().getOwner();

        Context context=new Context();
        context.setVariable("ownerName", owner.getName());
        context.setVariable("adminName", admin.getName());
        context.setVariable("theatherName", theater.getName());
        context.setVariable("address", theater.getAddress());
        context.setVariable("state", theater.getState());
        context.setVariable("pincode", theater.getPinCode());

        String htmlMail=templateEngine.process("AcceptTheaterRequest",context);

        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message);
        helper.setSubject("theater approval confirmation");
        helper.setTo(owner.getEmail());
        helper.setText(htmlMail,true);
        javaMailSender.send(message);
    }
}
