package bms.example.auth.api.Service;

import bms.example.auth.api.Integrations.DbApi;
import bms.example.auth.api.Models.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    @Value("${auth.secret.key}")
    String secretKey;

    @Autowired
    DbApi dbApi;

    Long expirationTime=1000000L;
    public String generateToken(String userId, String password){
        String credential=userId+":"+password;
        String jwtToken= Jwts.builder().setSubject(credential)
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return  jwtToken;
    }

    public String decryptToken(String token){
        String credentials=Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        return credentials;
    }

    public boolean verifyToken(String token){
        String credentials=this.decryptToken(token);
        String email=credentials.split(":")[0];
        String password=credentials.split(":")[1];

        AppUser user=dbApi.findUserByEmail(email);
        if(password.equals(user.getPassword())){
            return true;
        }
        return false;
    }
}
