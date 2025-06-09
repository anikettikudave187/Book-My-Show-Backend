package com.bms.central_api_v1.util;

import com.bms.central_api_v1.Enums.TheaterStatus;
import com.bms.central_api_v1.RequestBody.CreateTheaterRB;
import com.bms.central_api_v1.RequestBody.CreateUserRB;
import com.bms.central_api_v1.models.AppUser;
import com.bms.central_api_v1.models.Theater;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public AppUser mapCreateUserRBToAppUser(CreateUserRB createUserRB){
        AppUser appUser = new AppUser();
        appUser.setName(createUserRB.getName());
        appUser.setEmail(createUserRB.getEmail());
        appUser.setPassword(createUserRB.getPassword());
        appUser.setPincode(createUserRB.getPincode());
        appUser.setAddress(createUserRB.getAddress());
        appUser.setState(createUserRB.getState());
        appUser.setPhoneNo(createUserRB.getPhoneNo());
        appUser.setUserType(createUserRB.getUserType().toString());
        return appUser;
    }

    public Theater mapTheatherRBToTheatherModel(CreateTheaterRB theatherRB, AppUser owner){
        Theater theather = new Theater();
        theather.setAddress(theather.getAddress());
        theather.setOwner(owner);
        theather.setStatus(TheaterStatus.REQUEST_RAISED.toString());
        theather.setPincode(theather.getPincode());
        theather.setState(theatherRB.getState());
        theather.setName(theatherRB.getName());
        return theather;
    }
}
