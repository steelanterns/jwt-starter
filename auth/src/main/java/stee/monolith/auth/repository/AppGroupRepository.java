package stee.monolith.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import stee.monolith.auth.entity.AppGroup;

import java.util.Optional;

public interface AppGroupRepository extends JpaRepository<AppGroup, Long> {
    Optional<AppGroup> findByGroupName(String groupName );
}
