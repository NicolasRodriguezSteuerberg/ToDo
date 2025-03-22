package com.nsteuerberg.todo.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String jwtSecret;

    @Value("${jwt.user.generator}")
    private String userGenerator;

    @Value("${jwt.token.expire-minutes}")
    private int EXPIRETIMEMINUTES;

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null){
            throw new IllegalArgumentException("Authentication invalid");
        }
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        String username = authentication.getPrincipal().toString();
        log.info("Generating token for {}", username);
        List<String> autorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(username)
                .withClaim("authorities", autorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(EXPIRETIMEMINUTES)))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

}