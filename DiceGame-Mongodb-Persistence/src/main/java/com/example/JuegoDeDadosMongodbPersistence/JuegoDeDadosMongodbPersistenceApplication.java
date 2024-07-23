package com.example.JuegoDeDadosMongodbPersistence;

import com.example.JuegoDeDadosMongodbPersistence.Model.RoleEntity;
import com.example.JuegoDeDadosMongodbPersistence.Model.UserEntity;
import com.example.JuegoDeDadosMongodbPersistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class JuegoDeDadosMongodbPersistenceApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(JuegoDeDadosMongodbPersistenceApplication.class, args);
	}

	@Bean
	CommandLineRunner innit() {
		return args -> {
			UserEntity admin = UserEntity.builder()
					.email("alex@gmail.com")
					.name("alex")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
								.role(RoleEntity.ERole.ADMIN).build()))
					.build();

			UserEntity user = UserEntity.builder()
					.email("jan@gmail.com")
					.name("jan")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
								.role(RoleEntity.ERole.USER).build()))
					.build();


			Optional<UserEntity> adminByEmail = userRepository.findByEmail("alex@gmail.com");
			Optional<UserEntity> userByEmail = userRepository.findByEmail("jan@gmail.com");

			if (adminByEmail.isEmpty()) {
				System.out.println("Creating admin");
				userRepository.save(admin);
			}

			if (userByEmail.isEmpty()) {
				System.out.println("Creating user");
				userRepository.save(user);
			}
		};
	}

}
