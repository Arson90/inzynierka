package pl.arek.inzynierka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.arek.inzynierka.data.Roles;
import pl.arek.inzynierka.data.User;
import pl.arek.inzynierka.data.UsersRoles;
import pl.arek.inzynierka.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "eMail/{email}")
    public String eMail(@PathVariable("email") String email){

        User user = userRepository.findByUserName(email);
        return user.toString();
    }

    @RequestMapping(method = RequestMethod.POST,value = "eMail")
    public void saveUser() {
        UsersRoles usersRoles = new UsersRoles();
        usersRoles.setRole(Roles.ADMINISTRATOR);
        HashSet<UsersRoles> roles = new HashSet<>();
        roles.add(usersRoles);
        User user = new User("tomaszDupa", "test", roles);
        userRepository.save(user);
    }
}
