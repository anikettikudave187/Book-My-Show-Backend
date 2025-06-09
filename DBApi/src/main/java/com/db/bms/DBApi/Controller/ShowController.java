package com.db.bms.DBApi.Controller;

import com.db.bms.DBApi.ModelClasses.Shows;
import com.db.bms.DBApi.Repository.ShowRepo;
import com.db.bms.DBApi.ResponseBody.ShowsByHallResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/db")
public class ShowController {
    @Autowired
    ShowRepo showRepo;

    @PostMapping("/create")
    public ResponseEntity createShow(@RequestBody Shows shows){
        showRepo.save(shows);
        return new ResponseEntity(shows, HttpStatus.CREATED);
    }

    @GetMapping("{showId}")
    public ResponseEntity findShowById(@RequestParam UUID showId){
        Shows show=showRepo.findById(showId).orElse(null);
        return new ResponseEntity(show,HttpStatus.OK);
    }

    @GetMapping("{hallId}")
    public ResponseEntity findShowByHallId(@RequestParam UUID hallId){
        List<Shows> show=showRepo.getShowsByHallId(hallId);
        ShowsByHallResponseBody showsByHallResponseBody=new ShowsByHallResponseBody();
        showsByHallResponseBody.setShows(show);
        return new ResponseEntity(showsByHallResponseBody,HttpStatus.OK);
    }
}
