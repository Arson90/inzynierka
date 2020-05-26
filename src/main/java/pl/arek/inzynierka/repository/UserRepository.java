package pl.arek.inzynierka.repository;

import org.springframework.data.repository.CrudRepository;
import pl.arek.inzynierka.data.UserInternal;

public interface UserRepository extends CrudRepository<UserInternal, Long> {

    UserInternal findByUserName(String userName);

}
