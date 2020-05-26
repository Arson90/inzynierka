package pl.arek.inzynierka;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.arek.inzynierka.data.UserInternal;
import pl.arek.inzynierka.repository.UserRepository;

import java.util.HashSet;

@SpringBootApplication
public class InzynierkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InzynierkaApplication.class, args);
	}

	@Bean
	public ApplicationRunner inicjalizer(UserRepository userRepository, BCryptPasswordEncoder encoder){

		return args -> userRepository.save(new UserInternal("tomasz", encoder.encode("test"), new HashSet<>()));
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}

