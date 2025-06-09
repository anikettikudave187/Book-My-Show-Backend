package com.bms.central_api_v1.Service;

import com.bms.central_api_v1.RequestBody.CreateMovieRB;
import com.bms.central_api_v1.integrations.DBApi;
import com.bms.central_api_v1.models.AppUser;
import com.bms.central_api_v1.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovieService {
    @Autowired
    DBApi dbApi;

    @Autowired
    UserService userService;

    public Movie findMovieById(UUID movieId){
        return dbApi.callFindMovieByIdEndpoint(movieId);
    }

    public Movie createMovie(CreateMovieRB movieRB,UUID movieOwnerId){
        AppUser owner=userService.getUserById(movieOwnerId);
        Movie movie=new Movie();
        movie.setMovieOwner(owner);
        movie.setDuration(movieRB.getDuration());
        movie.setName(movieRB.getName());
        movie.setLanguage(movieRB.getLanguage());
        movie.setReleased(movieRB.isReleased());
        movie.setReview(0);
        movie.setTotalReviewVotes(0);

        return dbApi.callCreateMovieEndpoint(movie);
    }
}
