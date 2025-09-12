package com.example.store.auth;

import com.example.store.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfiguration jwtConfiguration;
    public Jwt generateAccessToken(User user){
        return generateToken(user, jwtConfiguration.getAccessTokenExpiration());
    }
    public Jwt generateRefreshToken(User user){
        return generateToken(user, jwtConfiguration.getRefreshTokenExpiration());
    }
    private Jwt generateToken(User user, long tokenExpiration) {
        Claims claims = Jwts.claims()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();
        return  new Jwt(claims, jwtConfiguration.getSecretKey());
    }

    public Jwt parse(String token){
        try {
            var claims = Jwts.parser()
                    .verifyWith(jwtConfiguration.getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return new Jwt(claims, jwtConfiguration.getSecretKey());
        }
        catch (JwtException exception){
            return null;
        }
    }
}
