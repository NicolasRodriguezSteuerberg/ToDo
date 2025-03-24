package com.nsteuerberg.todo;

import com.nsteuerberg.todo.persistance.entity.PermissionEntity;
import com.nsteuerberg.todo.persistance.entity.RoleEntity;
import com.nsteuerberg.todo.persistance.entity.UserEntity;
import com.nsteuerberg.todo.persistance.repository.RoleRepository;
import com.nsteuerberg.todo.persistance.repository.UserRepository;
import com.nsteuerberg.todo.util.constant.PermissionEnum;
import com.nsteuerberg.todo.util.constant.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			// create permissions
			PermissionEntity createPermission = PermissionEntity.builder()
					.permissionName(PermissionEnum.WRITE)
					.build();
			PermissionEntity readPermission = PermissionEntity.builder()
					.permissionName(PermissionEnum.READ)
					.build();
			PermissionEntity updatePermission = PermissionEntity.builder()
					.permissionName(PermissionEnum.UPDATE)
					.build();
			PermissionEntity deletePermission = PermissionEntity.builder()
					.permissionName(PermissionEnum.DELETE)
					.build();

			// create roles
			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissionList(Set.of(createPermission, readPermission))
					.build();

			RoleEntity roleDeveloper = RoleEntity.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			UserEntity userNico = UserEntity.builder()
					.userName("nico")
					.userEmail("nico@gmail.com")
					.userPassword("$argon2id$v=19$m=16384,t=2,p=1$vJxb+NSI1JCIkWjcc4cr6g$bL+5FO2wcY+J5BAOMw/bcjPiNldu2f7FhkNmF/aNF1k")
					.roleList(Set.of(roleDeveloper))
					.build();
			UserEntity userIrene = UserEntity.builder()
					.userName("irene")
					.userEmail("irene@gmail.com")
					.userPassword("$argon2id$v=19$m=16384,t=2,p=1$vJxb+NSI1JCIkWjcc4cr6g$bL+5FO2wcY+J5BAOMw/bcjPiNldu2f7FhkNmF/aNF1k")
					.roleList(Set.of(roleUser))
					.build();
			userRepository.saveAll(List.of(userNico, userIrene));

		};
	}
}
