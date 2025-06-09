package com.bms.central_api_v1.Service;

import com.bms.central_api_v1.Enums.UserType;
import com.bms.central_api_v1.RequestBody.CreateUserRB;
import com.bms.central_api_v1.exceptions.UserNotFoundException;
import com.bms.central_api_v1.integrations.DBApi;
import com.bms.central_api_v1.models.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    DBApi dbApi;

    public Object registerUser(CreateUserRB userRB){
        log.info("recieved call from userController to register user"+ userRB.toString());
        Object resp =dbApi.callCreateUserEndpoint(userRB);
        return resp;
    }
    public AppUser getUserById(UUID userId){
       return dbApi.callGetUserByIdEndpoint(userId);
    }

    public boolean isTheaterOwner(UUID theaterOwnerId){
        AppUser theaterOwner=getUserById(theaterOwnerId);
        if(theaterOwner==null){
            throw new UserNotFoundException(String.format("invalid theater owner id %s", theaterOwnerId.toString()));
        }
        return theaterOwner.getUserType().equals(UserType.THEATHER_OWNER.toString())?true:false;
    }
    public List<AppUser> getAllAdmins(){
        return dbApi.callGetAllAdminsEndpoint();
    }
}
