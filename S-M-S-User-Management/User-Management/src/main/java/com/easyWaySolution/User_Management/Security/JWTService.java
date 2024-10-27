package com.easyWaySolution.User_Management.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {

    private final SecretKey secretKey ;

    // Constructor: Generate the key once and store it
    public JWTService() {
        //    @Value("${jwt.secret}")
        String secretKeyBase64 = "U2FsdGVkX19gZWF1Z9cA4O6qR9cB2b8sbT0t3U8IwOQ";
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyBase64));
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
