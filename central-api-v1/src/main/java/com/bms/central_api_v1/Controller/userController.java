package com.bms.central_api_v1.Controller;

import com.bms.central_api_v1.RequestBody.CreateUserRB;
import com.bms.central_api_v1.Service.UserService;
import com.bms.central_api_v1.models.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/user")
@Slf4j
public class userController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
   public Object registerUser(@RequestBody CreateUserRB userRB){
        return userService.registerUser(userRB);
    }
    @GetMapping("/{userId}")
    public AppUser getUserById(@PathVariable UUID userId){
        return userService.getUserById(userId);
    }

}
