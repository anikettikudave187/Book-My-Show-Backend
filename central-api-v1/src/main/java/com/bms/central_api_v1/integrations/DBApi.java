package com.bms.central_api_v1.integrations;

import com.bms.central_api_v1.RequestBody.CreateMovieRB;
import com.bms.central_api_v1.RequestBody.CreateTheaterRB;
import com.bms.central_api_v1.RequestBody.CreateUserRB;
import com.bms.central_api_v1.models.*;
import com.bms.central_api_v1.responseBody.AdminResponseBody;
import com.bms.central_api_v1.responseBody.ShowByHallResponseBody;
import com.bms.central_api_v1.util.Mapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class DBApi extends RestApi{

    private static final Logger log = LoggerFactory.getLogger(DBApi.class);
    @Value("${db.api.base}")
    String baseUrl;

    @Autowired
    Mapper mapper;

    @Autowired
    ModelMapper modelMapper;

    public AppUser callCreateUserEndpoint(CreateUserRB createUserRB){
        AppUser appUser=mapper.mapCreateUserRBToAppUser(createUserRB);
        String endpoint="/user/create";
        log.info("calling /user/create endpoint of dbapi");
        Object resp=this.makePostCall(baseUrl,endpoint,appUser,new HashMap<>());
        AppUser userResp=modelMapper.map(resp,AppUser.class);
        return userResp;
    }

    public AppUser callGetUserByIdEndpoint(UUID userId){
        String endpoint="/user/"+userId.toString();
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        if(resp==null){
            return null;
        }
        return modelMapper.map(resp,AppUser.class);
    }

    public Theater callGetTheaterByIdEndpoint(UUID theaterId){
        String endpoint="/theater/"+theaterId.toString();
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        if(resp==null){
            return null;
        }
        return modelMapper.map(resp,Theater.class);
    }

    public List<AppUser> callGetAllAdminsEndpoint(){
        String endpoint="/user/admins";
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        AdminResponseBody allAdmins=modelMapper.map(resp,AdminResponseBody.class);
        return allAdmins.getAdmins();
    }

    public Theater callCreateTheaterEndpoint(CreateTheaterRB theaterRB,AppUser owner){
        Theater theater=mapper.mapTheatherRBToTheatherModel(theaterRB,owner);
        String endpoint="/theater/create";
        Object resp=this.makePostCall(baseUrl,endpoint,theater,new HashMap<>());
        return modelMapper.map(resp,Theater.class);
    }
    public Theater callGetTheaterById(UUID theaterId){
        String endpoint="/theater/"+theaterId.toString();
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        return modelMapper.map(resp,Theater.class);
    }

    public Theater callUpdateTheaterEndpoint(Theater theater){
        String endpoint="/theater/update";
        Object resp=this.makePutCall(baseUrl,endpoint,theater,new HashMap<>());
        return modelMapper.map(resp,Theater.class);
    }

    public Movie callCreateMovieEndpoint(Movie movie){
        String endpoint="/movie/create";
        Object resp=this.makePostCall(baseUrl,endpoint,movie,new HashMap<>());
        return modelMapper.map(resp,Movie.class);
    }

    public Movie callFindMovieByIdEndpoint(UUID movieId){
        String endpoint="/movie/"+movieId.toString();
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        return modelMapper.map(resp,Movie.class);
    }

    public Hall callFindHallByIdEndpoint(UUID hallId){
        String endpoint="/hall/"+hallId.toString();
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        return modelMapper.map(resp,Hall.class);
    }

    public Hall callCreateHallEndpoint(Hall hall){
        String endpoint="/hall/create";
        Object resp=this.makePostCall(baseUrl,endpoint,hall,new HashMap<>());
        return modelMapper.map(resp,Hall.class);
    }

    public Show callFindShowByIdEndpoint(UUID showId){
        String endpoint="/show/"+showId.toString();
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        return modelMapper.map(resp,Show.class);
    }

    public Show callCreateShowEndpoint(Show show){
        String endpoint="/show/create";
        Object resp=this.makePostCall(baseUrl,endpoint,show,new HashMap<>());
        return modelMapper.map(resp,Show.class);
    }

    public ShowByHallResponseBody getShowsByHallId(UUID hallId){
        String endpoint="/show/hall/"+hallId.toString();
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        return modelMapper.map(resp,ShowByHallResponseBody.class);
    }

    public BookedSeats callGetBookedSeatsEndpoint(UUID showId,int seatNo){
        String endpoint="/bookedSeat/check";
        HashMap<String, String>queryParams=new HashMap<>();
        queryParams.put("showId",showId.toString());
        queryParams.put("seat",seatNo+"");
        Object resp=this.makeGetCall(baseUrl,endpoint,queryParams);
        if(resp==null){
            return null;
        }
        return modelMapper.map(resp,BookedSeats.class);
    }

    public BookedSeats callCreateBookedSeatEndpoint(BookedSeats bookedSeats){
        String endpoint="/bookedSeat/create";
        Object resp=this.makePostCall(baseUrl,endpoint,bookedSeats,new HashMap<>());
        return modelMapper.map(resp,BookedSeats.class);
    }
}
