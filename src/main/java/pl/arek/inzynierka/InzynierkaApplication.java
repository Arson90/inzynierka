package pl.arek.inzynierka;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.arek.inzynierka.data.Client;
import pl.arek.inzynierka.data.Roles;
import pl.arek.inzynierka.data.UserInternal;
import pl.arek.inzynierka.data.UsersRoles;
import pl.arek.inzynierka.repository.ClientRepository;
import pl.arek.inzynierka.repository.RoleRepository;
import pl.arek.inzynierka.repository.UserRepository;

import java.util.Collections;

@SpringBootApplication
public class InzynierkaApplication {
	public static void main(String[] args) {
		SpringApplication.run(InzynierkaApplication.class, args);
	}

	@Bean
	public ApplicationRunner inicjalizer(UserRepository userRepository, BCryptPasswordEncoder encoder, RoleRepository roleRepository, ClientRepository clientRepository){
		UsersRoles administrator = roleRepository.save(new UsersRoles(Roles.ROLE_ADMINISTRATOR));
		UsersRoles employee = roleRepository.save(new UsersRoles(Roles.ROLE_EMPLOYEE));
		UsersRoles client = roleRepository.save(new UsersRoles(Roles.ROLE_CLIENT));
		return args -> {
			userRepository.save(new UserInternal("admin", encoder.encode("test"), Collections.singleton(administrator)));
			userRepository.save(new UserInternal("employee", encoder.encode("test"), Collections.singleton(employee)));
			UserInternal userForClient = userRepository.save(new UserInternal("client", encoder.encode("test"), Collections.singleton(client)));
			clientRepository.save(new Client("client", "client", 123, Collections.emptyList(), userForClient));
			userRepository.save(new UserInternal("norole", encoder.encode("test")));
		};
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}

