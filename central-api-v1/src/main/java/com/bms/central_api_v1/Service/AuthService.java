package com.bms.central_api_v1.Service;

import com.bms.central_api_v1.exceptions.UnAuthorizedException;
import com.bms.central_api_v1.integrations.AuthApi;
import com.bms.central_api_v1.responseBody.SucessResponseBody;
import com.bms.central_api_v1.responseBody.TokenResponseBody;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthApi authApi;

    @Autowired
    ModelMapper modelMapper;

    public void verifyToken(String authorization){
        try{
            SucessResponseBody sucessResponseBody=authApi.callVerifyTokenEndpoint(authorization);
        }catch(UnAuthorizedException e){
            throw new UnAuthorizedException(String.format("invalid token"));
        }
    }

    public String getToken(String email,String password){
        Object resp=authApi.callGenerateTokenEndpoint(email,password);
        TokenResponseBody tokenRb=modelMapper.map(resp,TokenResponseBody.class);
        return tokenRb.getToken();

    }
}
