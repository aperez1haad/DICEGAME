package com.example.JuegoDeDadosMongodbPersistence.Security.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JWTUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    //Crear token

    public String generateToken(String username){

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignature(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Validar el token de acceso

    public boolean isTokenValid(String token){
        try {
            getClaims(token);
            return true;
        }catch (Exception e){
            log.error("Error en la validación del token");
            return false;
        }
    }

    //Obtener el usuario del token
    public String getUsername(String token){
        return getClaim(token, Claims::getSubject);
    }



    //Obtener un claim
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = getClaims(token);
        return claimsTFunction.apply(claims);
    }

    //Obtener los claims del token

    public Claims extractClaims(String token){
        return getClaims(token);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignature())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSignature(){
        byte[] secretBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretBytes);
    }

}
