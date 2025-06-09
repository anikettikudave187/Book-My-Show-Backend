package com.db.bms.DBApi.Controller;

import com.db.bms.DBApi.ModelClasses.Movies;
import com.db.bms.DBApi.Repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/db")
public class MovieController {
    @Autowired
    MovieRepo movieRepo;

    @PostMapping("/create")
    public ResponseEntity createMovie(@RequestBody Movies movies){
        movieRepo.save(movies);
        return new ResponseEntity(movies, HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity findMovieById(@RequestParam UUID movieId){
        Movies movie=movieRepo.findById(movieId).orElse(null);
        return new ResponseEntity(movie,HttpStatus.OK);
    }
}
