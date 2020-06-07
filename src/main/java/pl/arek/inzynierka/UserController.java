package pl.arek.inzynierka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.arek.inzynierka.data.Roles;
import pl.arek.inzynierka.data.UserInternal;
import pl.arek.inzynierka.repository.RoleRepository;
import pl.arek.inzynierka.repository.UserRepository;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    //wylistowanie userów
    @RequestMapping(method = RequestMethod.GET, value = "users")
    public ResponseEntity getAllUsers(){

        Iterable<UserInternal> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    //dodawanie userów
    @RequestMapping(method = RequestMethod.POST,value = "users")
    public ResponseEntity saveUser(@RequestBody UserInternal userInternal) {

        return ResponseEntity.ok(userRepository.save(userInternal));
    }

    //modyfiakcja uprawnien userów
    @RequestMapping(method = RequestMethod.POST,value = "users/{id}/roles/{role}")
    public ResponseEntity addRoles(@PathVariable Long id, @PathVariable String role) {

        return userRepository.findById(id).map(userInternal -> addRole(userInternal, role))
                .map(userInternal -> ResponseEntity.ok(userInternal))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Optional<UserInternal> addRole(UserInternal userInternal, String role) {
        return roleRepository.findById(Roles.valueOf(role))
                .map(usersRoles -> {
                    userInternal.getRoles().add(usersRoles);
                    userRepository.save(userInternal);
                    return userInternal;
                });
    }
}
