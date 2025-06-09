package com.bms.central_api_v1.integrations;

import com.bms.central_api_v1.responseBody.SucessResponseBody;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AuthApi extends RestApi{
    @Value("${auth.api.base}")
    String authApiBase;

    @Autowired
    ModelMapper modelMapper;

    public Object callGenerateTokenEndpoint(String userId, String password){
        String endpoint="/token";
        HashMap queryParams=new HashMap();
        queryParams.put("userId",userId);
        queryParams.put("password",password);
        Object resp=this.makeGetCall(authApiBase,endpoint,queryParams);
        return resp;
    }

    public SucessResponseBody callVerifyTokenEndpoint(String token){
        String endpoint="/verify-token";
        Object resp=this.makeGetCall(authApiBase,endpoint,new HashMap<>());
        return modelMapper.map(resp,SucessResponseBody.class);
    }

}
