package stee.monolith.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stee.monolith.auth.entity.AppUser;
import stee.monolith.auth.service.AuthorityService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class AuthorityServiceImpl  implements AuthorityService {

    @Override
    public List<AppUser> findAll() {
        return null;
    }

    @Override
    public Optional<AppUser> findById(Long aLong) {
        return Optional.empty();
    }


    @Override
    public AppUser save(AppUser object) {
        return null;
    }

    @Override
    public void delete(AppUser object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

}
