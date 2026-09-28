//package com.ga.todo.security;
//
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Component
//public class JWTUtils {
//    Logger logger = Logger.getLogger(JWTUtils.class.getName());
//
//    @Value("${jwt-secret}")
//    private String jwtSecret;
//
//    @Value("${jwt-expiration-ms}")
//    private int jwtExpirationMs;
//
//    public String generateJwtToken(MyUserDetails myUserDetails) {
//        return Jwts.builder()
//                .setSubject((myUserDetails.getUsername()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS256, jwtSecret)
//                .compact();
//
//    }
//public String getUsernameFromJwtToken(String token){
//        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
//    }
//    public boolean validateJwtToken(String authToken){
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//        }catch (SecurityException e) {
//            logger.log(Level.SEVERE, "Invaild JWT Signature: {0}", e.getMessage());
//        }
//        return false;
//
//        }
//    }
//
package com.ga.todo.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtils {

    private static final Logger logger =
            LoggerFactory.getLogger(JWTUtils.class);

    @Value("${jwt-secret}")
    private String jwtSecret;

    @Value("${jwt-expiration-ms}")
    private int jwtExpirationMs;

    // Generate JWT
    public String generateJwtToken(MyUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + jwtExpirationMs
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(
                                jwtSecret.getBytes(StandardCharsets.UTF_8)
                        ),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    // Get username/email from JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(
                                jwtSecret.getBytes(StandardCharsets.UTF_8)
                        )
                )
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate JWT
    public boolean validateJwtToken(String authToken) {
        try {

            Jwts.parserBuilder()
                    .setSigningKey(
                            Keys.hmacShaKeyFor(
                                    jwtSecret.getBytes(StandardCharsets.UTF_8)
                            )
                    )
                    .build()
                    .parseClaimsJws(authToken);

            System.out.println("JWT VALID");

            return true;

        } catch (MalformedJwtException e) {

            System.out.println("Invalid JWT: " + e.getMessage());

        } catch (ExpiredJwtException e) {

            System.out.println("JWT EXPIRED: " + e.getMessage());

        } catch (UnsupportedJwtException e) {

            System.out.println("JWT UNSUPPORTED: " + e.getMessage());

        } catch (IllegalArgumentException e) {

            System.out.println("JWT EMPTY: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("JWT ERROR: " + e.getMessage());
        }

        return false;
    }
}
