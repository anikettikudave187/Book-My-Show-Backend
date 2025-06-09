package com.db.bms.DBApi.Controller;

import com.db.bms.DBApi.ModelClasses.Theater;
import com.db.bms.DBApi.Repository.TheaterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/db/theater")
public class TheaterController {

    @Autowired
    TheaterRepo theaterRepo;

    @PostMapping("/create")
    public ResponseEntity createTheater(@RequestBody Theater theater){
        theaterRepo.save(theater);
        return new ResponseEntity(theater, HttpStatus.CREATED);
    }

    @GetMapping("/{theaterId}")
    public ResponseEntity getTheaterById(@PathVariable UUID theaterId){
        Theater theater= theaterRepo.findById(theaterId).orElse(null);
        return new ResponseEntity(theater, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateTheater(@RequestBody Theater theater){
        theaterRepo.save(theater);
        return new ResponseEntity(theater, HttpStatus.OK);
    }
}
