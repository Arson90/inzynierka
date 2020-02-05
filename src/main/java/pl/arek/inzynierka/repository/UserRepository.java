package pl.arek.inzynierka.repository;

import org.springframework.data.repository.CrudRepository;
import pl.arek.inzynierka.data.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserName(String userName);

}
