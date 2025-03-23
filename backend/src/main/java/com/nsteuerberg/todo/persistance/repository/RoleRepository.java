package com.nsteuerberg.todo.persistance.repository;

import com.nsteuerberg.todo.persistance.entity.RoleEntity;
import com.nsteuerberg.todo.util.constant.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findRoleEntityByRoleEnum(RoleEnum roleEnum);
}
