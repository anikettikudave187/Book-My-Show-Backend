package bms.example.auth.api.Controller;

import bms.example.auth.api.ResponseBody.SuccessResponseBody;
import bms.example.auth.api.ResponseBody.TokenResponseBody;
import bms.example.auth.api.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @GetMapping("/token")
    public ResponseEntity getToken(@RequestParam String userId, @RequestParam String password){
        String jwtToken=authService.generateToken(userId,password);
        TokenResponseBody resp=new TokenResponseBody();
        resp.setToken(jwtToken);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @GetMapping("verify-token")
    public ResponseEntity verifyToken(){
        SuccessResponseBody successResponse=new SuccessResponseBody();
        successResponse.setStatus("Success");
        return new ResponseEntity(successResponse,HttpStatus.OK);
    }
}
