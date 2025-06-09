package com.bms.central_api_v1.Controller;

import com.bms.central_api_v1.RequestBody.CreateHallRB;
import com.bms.central_api_v1.Service.AuthService;
import com.bms.central_api_v1.Service.HallService;
import com.bms.central_api_v1.exceptions.UnAuthorizedException;
import com.bms.central_api_v1.models.Hall;
import com.bms.central_api_v1.responseBody.GeneralMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/hall")
public class HallController {

    @Autowired
    HallService hallService;

    @Autowired
    AuthService authService;

    @GetMapping("/{hallId}")
    public Hall findHallById(UUID hallId){
        return hallService.findHallById(hallId);
    }

    @PostMapping("/create")
    public ResponseEntity createHall(@RequestParam UUID ownerId,@RequestParam UUID theaterId, @RequestBody CreateHallRB hallRB, @RequestHeader String authorization){
        try{
            authService.verifyToken(authorization);
            Hall hall=hallService.createHall(ownerId,theaterId,hallRB);
            return new ResponseEntity(hall, HttpStatus.CREATED);
        }catch(UnAuthorizedException e){
            GeneralMessageResponse gm=new GeneralMessageResponse();
            gm.setMessage(e.getMessage());
            return new ResponseEntity(gm,HttpStatus.UNAUTHORIZED);
        }
    }
}
