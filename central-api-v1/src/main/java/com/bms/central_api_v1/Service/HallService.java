package com.bms.central_api_v1.Service;

import com.bms.central_api_v1.RequestBody.CreateHallRB;
import com.bms.central_api_v1.exceptions.UnAuthorizedException;
import com.bms.central_api_v1.integrations.DBApi;
import com.bms.central_api_v1.models.Hall;
import com.bms.central_api_v1.models.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HallService {
    @Autowired
    DBApi dbApi;

    @Autowired
    TheaterService theaterService;

    public Hall findHallById(UUID hallId){
        return dbApi.callFindHallByIdEndpoint(hallId);
    }

    public Hall createHall(UUID ownerId, UUID theaterId, CreateHallRB hallRB){
        Theater theater=theaterService.getTheaterById(theaterId);
        if(theater.getOwner().getUserId().equals(ownerId)){
            throw new UnAuthorizedException(String.format("theater eith id %s does not own theater with id %s", theaterId.toString(),ownerId.toString()));

        }
        Hall hall=new Hall();
        hall.setCapacity(hallRB.getCapacity());
        hall.setName(hallRB.getName());
        hall.setTheater(theater);

        return dbApi.callCreateHallEndpoint(hall);
    }
}
