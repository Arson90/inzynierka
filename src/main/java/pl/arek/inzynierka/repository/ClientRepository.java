package pl.arek.inzynierka.repository;

import org.springframework.data.repository.CrudRepository;
import pl.arek.inzynierka.data.Client;
import pl.arek.inzynierka.data.UserInternal;

import java.util.Optional;

//mechanizm spring crud repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    Optional<Client> findById(Long id);
    Optional<Client> findByUserInternal(UserInternal userInternal);
}
