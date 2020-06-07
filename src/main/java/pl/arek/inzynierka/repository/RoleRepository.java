package pl.arek.inzynierka.repository;

import org.springframework.data.repository.CrudRepository;
import pl.arek.inzynierka.data.Roles;
import pl.arek.inzynierka.data.UsersRoles;

public interface RoleRepository extends CrudRepository<UsersRoles, Roles> {
}
