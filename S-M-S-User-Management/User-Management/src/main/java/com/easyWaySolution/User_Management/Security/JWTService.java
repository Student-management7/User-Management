package com.easyWaySolution.User_Management.Security;

import com.easyWaySolution.User_Management.DTO.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.beans.Encoder;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {

    private final SecretKey secretKey;

    // Constructor: Generate the key once and store it
    public JWTService() {
        this.secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);  // Generates a 256-bit key
    }

    // Getter to retrieve the key for signing or verification
    public SecretKey getKey() {
        return this.secretKey;
    }

    public String  generateToken(String  userName ){
        Map<String  ,Object> claims = new HashMap<>();
       return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ( 15 * 60 * 1000)))
                .and()
                .signWith(getKey())
                .compact();
    }


// public Key keyGenerator(){
//      try {
//          KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
//          return Keys.hmacShaKeyFor(generator.generateKey().getEncoded());
//      } catch (NoSuchAlgorithmException e) {
//          throw new RuntimeException(e);
//      }
//
//  }

    public String extractUserName(String token) {

        return extractClaim(token , Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims , T> claimsTFunction) {
        final Claims claims = extractAllClaim(token);
        return  claimsTFunction.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }
//    public SecretKey getKey()  {
//        try {
//            KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
//            return generator.generateKey();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public boolean validateToken(String token , UserDetails userDetails) {
        final  String userName = extractUserName(token);
        return (userName.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token));

    }

    private boolean isTokenExpired(String token) {

        return extractExpiraton(token).before(new Date());
    }

    private Date extractExpiraton(String token) {
        return extractClaim(token , Claims::getExpiration);
    }
}
