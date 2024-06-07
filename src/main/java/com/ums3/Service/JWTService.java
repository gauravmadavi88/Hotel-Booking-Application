package com.ums3.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ums3.Entity.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service

public class JWTService {

    @Value("${jwt.Algorithm.key}")
    public String Algorithmkey;

    @Value("${jwt.Issuer}")
    public String Issuer;

    @Value("${jwt.Expiry.duration}")
    public int ExpiryTime;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(Algorithmkey);
    }

    public String generateToken(AppUser user){
        return JWT.create().withClaim("username",user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ExpiryTime))
                .withIssuer(Issuer)
                .sign(algorithm);
    }

    public String getUserName(String token){
        DecodedJWT decodeJWT = JWT.require(algorithm).withIssuer(Issuer).build().verify(token);
        return decodeJWT.getClaim("username").asString();
    }
}
