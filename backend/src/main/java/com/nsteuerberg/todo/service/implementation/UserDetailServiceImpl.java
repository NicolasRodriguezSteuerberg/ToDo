package com.nsteuerberg.todo.service.implementation;

import com.nsteuerberg.todo.persistance.entity.UserEntity;
import com.nsteuerberg.todo.persistance.repository.UserRepository;
import com.nsteuerberg.todo.presentation.dto.request.AuthLogInRequest;
import com.nsteuerberg.todo.presentation.dto.response.AuthTokensResponse;
import com.nsteuerberg.todo.util.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private JwtTokenProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Buscando usuario con el nombre {}", username);
        UserEntity userEntity = userRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException("Username invalid")
        );
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // get all roles
        userEntity.getRoleList().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(
                        "ROLE_".concat(role.getRoleEnum().name())
                ))
        );

        // get permissions
        userEntity.getRoleList().stream()
                .flatMap(role-> role.getPermissionList().stream())
                .forEach(permission ->
                        authorities.add(new SimpleGrantedAuthority(
                                permission.getPermissionName().name())
                        )
                );
        return new User(username,userEntity.getUserPassword(),authorities);
    }

    //ToDo hacer un login de verdad
    public AuthTokensResponse login(AuthLogInRequest logInRequest) {
        Authentication authentication = authenticate(logInRequest);
        String accessToken = jwtProvider.generateToken(authentication);
        return new AuthTokensResponse(
                "No hay de momento",
                accessToken
        );
    }

    private Authentication authenticate(AuthLogInRequest logInRequest) {
        UserDetails userDetails = loadUserByUsername(logInRequest.username());
        // no hace falta comprobar que exista userDetails ya que loadUserByUsername ya devuelve la respuesta
        if (!passwordEncoder.matches(logInRequest.password(), userDetails.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }
}
