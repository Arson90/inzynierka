package pl.arek.inzynierka.repository;

import org.springframework.data.repository.CrudRepository;
import pl.arek.inzynierka.data.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
