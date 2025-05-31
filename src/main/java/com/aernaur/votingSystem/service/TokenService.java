package com.aernaur.votingSystem.service;

import com.aernaur.votingSystem.entity.Login;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${security.jwt.secret}")
    private String secret;

    public String generateToken(Login login) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                      .withIssuer("OVS")
                      .withSubject(login.getUsername())
                      .withExpiresAt(genExpiresAt())
                      .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Unable to generate JWT token", e);
        }
    }

    private Instant genExpiresAt() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                      .withIssuer("OVS")
                      .build()
                      .verify(token)
                      .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }
}
