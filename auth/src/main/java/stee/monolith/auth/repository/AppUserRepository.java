package stee.monolith.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import stee.monolith.auth.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username );
    AppUser findByEmail( String email );
}
