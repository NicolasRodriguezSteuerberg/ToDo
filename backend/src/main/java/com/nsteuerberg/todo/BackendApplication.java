package com.nsteuerberg.todo;

import com.nsteuerberg.todo.persistance.entity.PermissionEntity;
import com.nsteuerberg.todo.persistance.entity.RoleEntity;
import com.nsteuerberg.todo.persistance.entity.UserEntity;
import com.nsteuerberg.todo.persistance.repository.UserRepository;
import com.nsteuerberg.todo.util.constant.PermissionEnum;
import com.nsteuerberg.todo.util.constant.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

			// create users
			UserEntity userNico = UserEntity.builder()
					.userName("nico")
					.userPassword("1234")
					.roleList(Set.of(roleDeveloper))
					.build();
			userRepository.save(userNico);
		};
	}
}
