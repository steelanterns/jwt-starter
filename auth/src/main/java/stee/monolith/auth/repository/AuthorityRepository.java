package stee.monolith.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import stee.monolith.auth.entity.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthority( String authority );
}
