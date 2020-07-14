package pl.arek.inzynierka;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.arek.inzynierka.data.Roles;
import pl.arek.inzynierka.data.UserInternal;
import pl.arek.inzynierka.data.UsersRoles;
import pl.arek.inzynierka.repository.RoleRepository;
import pl.arek.inzynierka.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class InzynierkaApplication {
	public static void main(String[] args) {
		SpringApplication.run(InzynierkaApplication.class, args);
	}

	@Bean
	public ApplicationRunner inicjalizer(UserRepository userRepository, BCryptPasswordEncoder encoder, RoleRepository roleRepository){
		UsersRoles administrator = roleRepository.save(new UsersRoles(Roles.ADMINISTRATOR));
		roleRepository.save(new UsersRoles(Roles.EMPLOYEE));
		roleRepository.save(new UsersRoles(Roles.CLIENT));
		return args -> {
			userRepository.save(new UserInternal("admin", encoder.encode("admin"), Collections.singleton(administrator)));
			userRepository.save(new UserInternal("tomasz", encoder.encode("test")));
		};
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}

