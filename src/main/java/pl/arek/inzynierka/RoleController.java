package pl.arek.inzynierka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.arek.inzynierka.data.UserInternal;
import pl.arek.inzynierka.data.UsersRoles;
import pl.arek.inzynierka.repository.RoleRepository;
import pl.arek.inzynierka.repository.UserRepository;

@RestController
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    //listowanie roles
    @RequestMapping(method = RequestMethod.GET, value = "roles")
    public ResponseEntity getAllRoles(){

        Iterable<UsersRoles> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }
}
