package pl.arek.inzynierka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import pl.arek.inzynierka.data.Client;
import pl.arek.inzynierka.data.UserInternal;
import pl.arek.inzynierka.repository.ClientRepository;
import pl.arek.inzynierka.repository.UserRepository;

import java.util.Optional;

@Component("userSecurity")
public class UserSecurity {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    public boolean hasClientOwnID(Authentication authentication, Long clientID) {
        String username = (String) authentication.getPrincipal();

        UserInternal loggedUser = userRepository.findByUsername(username);

        if(loggedUser != null) {
            return clientRepository.findById(clientID)
                    .map(Client::getUserInternal)
                    .map(UserInternal::getId)
                    .map(userInternalID -> userInternalID.equals(loggedUser.getId()))
                    .orElse(false);
        }
        return false;

    }
}
