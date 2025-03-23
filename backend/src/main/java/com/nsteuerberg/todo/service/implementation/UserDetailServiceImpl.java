package com.nsteuerberg.todo.service.implementation;

import com.nsteuerberg.todo.persistance.entity.RoleEntity;
import com.nsteuerberg.todo.persistance.entity.UserEntity;
import com.nsteuerberg.todo.persistance.repository.RoleRepository;
import com.nsteuerberg.todo.persistance.repository.UserRepository;
import com.nsteuerberg.todo.presentation.dto.request.AuthLogInRequest;
import com.nsteuerberg.todo.presentation.dto.request.AuthRegisterCompletedRequest;
import com.nsteuerberg.todo.presentation.dto.response.AuthTokensResponse;
import com.nsteuerberg.todo.util.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Autowired
    private RoleRepository roleRepository;

    private static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Buscando usuario con el nombre {}", username);
        UserEntity userEntity = userRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException("Username invalid")
        );
        Set<SimpleGrantedAuthority> authorities = getAuthorities(userEntity.getRoleList());

        return new User(username, userEntity.getUserPassword(), authorities);
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

    public AuthTokensResponse register(AuthRegisterCompletedRequest registerRequest) {
        if (!registerRequest.password().equals(registerRequest.confirmPassword())) {
            throw new BadCredentialsException("Passwords must be the same");
        }
        RoleEntity roleEntity = roleRepository.findRoleEntityByRoleEnum(registerRequest.role()).orElseThrow(() ->
                new BadCredentialsException("Role " + registerRequest.role().name() + " don't exits")
        );
        try{
            UserEntity newUser = userRepository.save(
                    UserEntity.builder()
                            .userName(registerRequest.username())
                            .userEmail(registerRequest.email())
                            .userPassword(passwordEncoder.encode(registerRequest.password()))
                            .roleList(Set.of(roleEntity))
                            .build()
            );
            Set<SimpleGrantedAuthority> authorities = getAuthorities(newUser.getRoleList());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    newUser.getUserName(),
                    newUser.getUserPassword(),
                    authorities
            );
            return new AuthTokensResponse(
                    "No existe de momento",
                    jwtProvider.generateToken(authentication)
            );
        } catch (DataIntegrityViolationException e) {
            // ToDo add logic to see what is used, user or email
            throw new BadCredentialsException("Username or email is already used, select another");
        }
    }


    private Set<SimpleGrantedAuthority> getAuthorities(Set<RoleEntity> roleList) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roleList.stream()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority(
                        "ROLE_".concat(role.getRoleEnum().name())
                )));

        roleList.stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission ->
                        authorities.add(new SimpleGrantedAuthority(
                                permission.getPermissionName().name()
                        ))
                );

        return authorities;
    }
}
