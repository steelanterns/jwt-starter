package stee.monolith.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stee.monolith.auth.entity.AppUser;
import stee.monolith.auth.repository.AppUserRepository;

import java.util.Optional;

/**
 * Created by Steeve Jean Chilles on 09/22/2023
 */

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = appUserRepository.findByUsername(username);

        if ( !user.isPresent() ) {
            throw new UsernameNotFoundException("username " + username + " is not found");
        }
//        return new User( steeUserDetails.getUsername(), steeUserDetails.getPassword(), steeUserDetails.getAuthorities() );
        return user.get();

//        return user.map( new SteeUserDetails::new )
//                .orElseThrow( () -> new UsernameNotFoundException( "User not found") );
    }
}
