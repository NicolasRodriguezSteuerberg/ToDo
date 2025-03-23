package com.nsteuerberg.todo.configuration.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nsteuerberg.todo.util.jwt.JwtTokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;


public class JwtTokenFilter extends OncePerRequestFilter {
    private JwtTokenValidator jwtValidator;

    public JwtTokenFilter(JwtTokenValidator jwtValidator){
        this.jwtValidator = jwtValidator;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtToken != null){
            try {
                // quitamos el BEARER + space
                jwtToken = jwtToken.substring(7);
                DecodedJWT decodedJWT = jwtValidator.validateToken(jwtToken);
                String username = decodedJWT.getSubject();
                Collection<?extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(
                        decodedJWT.getClaim("authorities").asList(String.class)
                );
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities
                        )
                );
                SecurityContextHolder.setContext(context);
            } catch (JWTVerificationException e){
                // respondemos con una respuesta personalizada
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
