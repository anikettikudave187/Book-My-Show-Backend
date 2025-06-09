package com.db.bms.DBApi.Controller;

import com.db.bms.DBApi.ModelClasses.BookedSeats;
import com.db.bms.DBApi.Repository.BookedSeatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/db")
public class BookedSeatController {
    @Autowired
    BookedSeatRepo bookedSeatRepo;

    @GetMapping("/check")
    public ResponseEntity isSeatBooked(@RequestParam UUID showId, @RequestParam int seatNo){
        BookedSeats bookedSeat=bookedSeatRepo.getBookedSeat(showId,seatNo);
        return new ResponseEntity(bookedSeat, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity bookSeat(@RequestBody BookedSeats bookedSeats){
        bookedSeatRepo.save(bookedSeats);
        return new ResponseEntity(bookedSeats,HttpStatus.CREATED);
    }
}
