package bms.example.auth.api.Integrations;

import bms.example.auth.api.Models.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class DbApi extends RestApi{

    @Value("${db.api.base}")
    String dbApiBaseUrl;

    @Autowired
    ModelMapper modelMapper;

    public AppUser findUserByEmail(String email){
        String endpoint="/user/email/"+email;
        Object resp=this.makeGetCall(dbApiBaseUrl,endpoint, new HashMap<>());
        if(resp==null){
            return null;
        }
        AppUser user= modelMapper.map(resp,AppUser.class);
        return user;
    }
}
