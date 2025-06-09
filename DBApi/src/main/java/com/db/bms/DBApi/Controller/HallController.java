package com.db.bms.DBApi.Controller;

import com.db.bms.DBApi.ModelClasses.Hall;
import com.db.bms.DBApi.Repository.HallRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/db")
public class HallController {
    @Autowired
    HallRepo hallRepo;

    @PostMapping("/create")
    public ResponseEntity createHall(@RequestBody Hall hall){
        hallRepo.save(hall);
        return new ResponseEntity(hall, HttpStatus.CREATED);
    }

    @GetMapping("/{hallId}")
    public ResponseEntity getHallById(@RequestParam UUID hallId){
        Hall hall=hallRepo.findById(hallId).orElse(null);
        return new ResponseEntity(hall,HttpStatus.OK);
    }
}
