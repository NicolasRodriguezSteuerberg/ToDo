package com.nsteuerberg.todo.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtTokenValidator {

    @Value("${jwt.token.secret}")
    private String secretKey;
    @Value("${jwt.user.generator}")
    private String userGenerator;

    Logger log = LoggerFactory.getLogger(JwtTokenValidator.class);

    public DecodedJWT validateToken(String jwtToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();
            return verifier.verify(jwtToken);
        } catch (JWTVerificationException e) {
            log.error("Error validando token");
            throw new JWTVerificationException("Token invalido");
        }
    }

    public String getSubject(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public Claim getClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    // necesario?
    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}
