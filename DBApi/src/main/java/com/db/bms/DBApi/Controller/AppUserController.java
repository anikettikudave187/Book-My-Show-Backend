package com.db.bms.DBApi.Controller;

import com.db.bms.DBApi.ModelClasses.AppUser;
import com.db.bms.DBApi.Repository.AppUserRepo;
import com.db.bms.DBApi.ResponseBody.AdminRB;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/db/user")
@Slf4j
public class AppUserController {

    private static final Logger log = LoggerFactory.getLogger(AppUserController.class);
    @Autowired
    AppUserRepo appUserRepo;

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody AppUser user){
        appUserRepo.save(user);
        log.info("recieved request with the requestbody: "+ user.toString());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@PathVariable UUID userId){
        AppUser user=appUserRepo.findById(userId).orElse(null);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateUserById(@RequestBody AppUser user){
        appUserRepo.save(user);
        return new ResponseEntity(user,HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUserById(@PathVariable UUID userId){
        appUserRepo.deleteById(userId);
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/admins")
    public ResponseEntity getAllAdmins(){
        List<AppUser> admins=appUserRepo.getAllAdmins();
        AdminRB response=new AdminRB();
        response.setAdmins(admins);
        return new ResponseEntity(response,HttpStatus.OK);
    }
}
