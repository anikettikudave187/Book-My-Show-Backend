package com.bms.central_api_v1.Controller;

import com.bms.central_api_v1.RequestBody.CreateShowRB;
import com.bms.central_api_v1.RequestBody.PurchaseTicketRB;
import com.bms.central_api_v1.Service.AuthService;
import com.bms.central_api_v1.Service.ShowService;
import com.bms.central_api_v1.exceptions.UnAuthorizedException;
import com.bms.central_api_v1.integrations.DBApi;
import com.bms.central_api_v1.models.BookedSeats;
import com.bms.central_api_v1.models.Show;
import com.bms.central_api_v1.responseBody.BillResponseBody;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/show")
public class ShowController {
    @Autowired
    ShowService showService;


    @PostMapping("/create")
    public ResponseEntity createShow( @RequestBody CreateShowRB showRB,@RequestParam UUID ownerId){
       Show show=showService.createShow(showRB,ownerId);
        try{
            return new ResponseEntity(show, HttpStatus.CREATED);
        }catch(UnAuthorizedException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/seats")
    public ResponseEntity checkAvailableSeats(@RequestParam UUID showId){
        List<Integer> availableSeats=showService.getAvailableSeatsInShow(showId);
        return new ResponseEntity(availableSeats,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity purchaseTickets(@RequestParam UUID userId, @RequestParam UUID showId, @RequestBody PurchaseTicketRB ticketRB){
        BillResponseBody bill=showService.purchaseTicketeForShow(userId,showId,ticketRB);
        return new ResponseEntity(bill,HttpStatus.OK);
    }
}
