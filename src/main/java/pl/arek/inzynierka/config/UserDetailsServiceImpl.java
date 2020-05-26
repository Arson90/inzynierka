package pl.arek.inzynierka.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.arek.inzynierka.data.UserInternal;
import pl.arek.inzynierka.repository.UserRepository;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInternal userInternal = userRepository.findByUserName(userName);
        if(userInternal ==null){
            throw new UsernameNotFoundException(userName);
        }
        return new org.springframework.security.core.userdetails.User(userInternal.getUserName(), userInternal.getPassword(), new ArrayList<>());
    }
}
