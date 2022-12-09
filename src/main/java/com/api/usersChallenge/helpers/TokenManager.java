package com.api.usersChallenge.helpers;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


public class TokenManager {



    private static final Long ACCESS_TOKEN_VALID_SECONDS = 2_592_000L;
    private static final String SECRET_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY3MDU0OTQ3NiwiaWF0IjoxNjcwNTQ5NDc2fQ.uS-woxoJVJbjCKTElxgA_7mUgT1Fl81dQklSoHkUKyc";
    public static String createToken(String name, String email) {
        long expirationTime = ACCESS_TOKEN_VALID_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> roles = new HashMap<>();
        roles.put("name", name);
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(roles)
                .signWith(Keys.hmacShaKeyFor(SECRET_ACCESS_TOKEN.getBytes()))
                .compact();

    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_ACCESS_TOKEN.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException ex) {
            return null;
        }
    }
}
