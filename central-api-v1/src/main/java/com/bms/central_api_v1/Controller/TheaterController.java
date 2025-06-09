package com.bms.central_api_v1.Controller;

import com.bms.central_api_v1.RequestBody.CreateTheaterRB;
import com.bms.central_api_v1.Service.AuthService;
import com.bms.central_api_v1.Service.TheaterService;
import com.bms.central_api_v1.exceptions.UnAuthorizedException;
import com.bms.central_api_v1.exceptions.UserNotFoundException;
import com.bms.central_api_v1.models.AppUser;
import com.bms.central_api_v1.models.Theater;
import com.bms.central_api_v1.responseBody.GeneralMessageResponse;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/theater")
public class TheaterController {
    @Autowired
    TheaterService theaterService;

    @Autowired
    AuthService authService;

    @PostMapping("/create")
    public ResponseEntity registerTheater(@RequestBody CreateTheaterRB theaterRB,
                                          @RequestParam UUID ownerId,
                                          @RequestHeader String authorization){
        try{
            authService.verifyToken(authorization);
            Theater theater=theaterService.raiseCreateTheaterRequest(theaterRB,ownerId,Authorization);
            return new ResponseEntity(theater, HttpStatus.CREATED);
        }catch(UnAuthorizedException e) {
            GeneralMessageResponse message=new GeneralMessageResponse();
            message.setMessage(e.getMessage());
            return new ResponseEntity(message,HttpStatus.UNAUTHORIZED);
        }catch (UserNotFoundException e){
            GeneralMessageResponse message=new GeneralMessageResponse();
            message.setMessage(e.getMessage());
            return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            GeneralMessageResponse message=new GeneralMessageResponse();
            message.setMessage(e.getMessage());
            return new ResponseEntity(message,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/update/{theaterId}/{adminId}/{token}")
    public ResponseEntity approveTheaterRequest(@PathVariable UUID theaterId,
                                                @PathVariable UUID adminId,
                                                @PathVariable String token){
       try{
           String bearerToken="bearer"+token;
           authService.verifyToken(bearerToken);
           theaterService.acceptTheaterRequest(adminId,theaterId);
           return new ResponseEntity(HttpStatus.OK);
       }catch(Exception e){
           GeneralMessageResponse message=new GeneralMessageResponse();
           message.setMessage(e.getMessage());
           return new ResponseEntity(message,HttpStatus.UNAUTHORIZED);
       }
    }


}
