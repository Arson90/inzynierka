package pl.arek.inzynierka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.arek.inzynierka.data.Roles;
import pl.arek.inzynierka.data.User;
import pl.arek.inzynierka.data.UsersRoles;
import pl.arek.inzynierka.repository.RoleRepository;
import pl.arek.inzynierka.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(method = RequestMethod.GET, value = "eMail/{email}")
    public String eMail(@PathVariable("email") String email){

        User user = userRepository.findByUserName(email);
        return user.toString();
    }

    @RequestMapping(method = RequestMethod.POST,value = "eMail")
    public void saveUser(@RequestBody User user) {
        userRepository.save(user);
    }
}
