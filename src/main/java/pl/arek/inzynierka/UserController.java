package pl.arek.inzynierka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.arek.inzynierka.data.UserInternal;
import pl.arek.inzynierka.repository.RoleRepository;
import pl.arek.inzynierka.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(method = RequestMethod.GET, value = "eMail/{email}")
    public String eMail(@PathVariable("email") String email){

        UserInternal userInternal = userRepository.findByUserName(email);
        return userInternal.toString();
    }

    @RequestMapping(method = RequestMethod.POST,value = "eMail")
    public void saveUser(@RequestBody UserInternal userInternal) {
        userRepository.save(userInternal);
    }
}
