package com.nsteuerberg.todo.service.implementation;

import com.nsteuerberg.todo.EndpointsListener;
import com.nsteuerberg.todo.persistance.entity.PermissionEntity;
import com.nsteuerberg.todo.persistance.entity.RoleEntity;
import com.nsteuerberg.todo.persistance.entity.UserEntity;
import com.nsteuerberg.todo.persistance.repository.UserRepository;
import com.nsteuerberg.todo.util.constant.PermissionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException("No existe el usuario")
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
}
