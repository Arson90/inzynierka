package pl.arek.inzynierka.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.arek.inzynierka.data.UserInternal;
import pl.arek.inzynierka.data.UsersRoles;
import pl.arek.inzynierka.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInternal userInternal = userRepository.findByUsername(userName);
        if(userInternal == null){
            throw new UsernameNotFoundException(userName);
        }
        Set<SimpleGrantedAuthority> authorities = userInternal.getRoles().stream()
                .map(UsersRoles::getRole)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(userInternal.getUsername(), userInternal.getPassword(), authorities);
    }
}
