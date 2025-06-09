package com.bms.central_api_v1.Controller;

import com.bms.central_api_v1.RequestBody.CreateMovieRB;
import com.bms.central_api_v1.Service.AuthService;
import com.bms.central_api_v1.Service.MovieService;
import com.bms.central_api_v1.exceptions.UnAuthorizedException;
import com.bms.central_api_v1.models.Movie;
import com.bms.central_api_v1.responseBody.GeneralMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/movie")
public class MovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    AuthService authService;

    public ResponseEntity createMovie(@RequestParam UUID movieId, @RequestBody CreateMovieRB movieRB, @RequestHeader String authorization){
        try{
            authService.verifyToken(authorization);
            Movie movie= movieService.createMovie(movieRB,movieId);
            return new ResponseEntity(movie, HttpStatus.OK);
        }catch (UnAuthorizedException e){
            GeneralMessageResponse gm=new GeneralMessageResponse();
            gm.setMessage(e.getMessage());
            return new ResponseEntity<>(gm,HttpStatus.UNAUTHORIZED);
        }
    }
}
