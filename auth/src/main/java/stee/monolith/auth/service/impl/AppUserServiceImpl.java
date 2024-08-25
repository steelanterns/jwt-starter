package stee.monolith.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stee.monolith.auth.entity.AppGroup;
import stee.monolith.auth.entity.AppUser;
import stee.monolith.auth.repository.AppGroupRepository;
import stee.monolith.auth.repository.AppUserRepository;
import stee.monolith.auth.service.AppUserService;

import java.util.List;
import java.util.Optional;

/**
 * Created by Steeve Jean Chilles on 09/22/2023
 */
@Service
@Slf4j
@Transactional
public class AppUserServiceImpl implements AppUserService {
    private AppUserRepository appUserRepository;
    private AppGroupRepository appGroupRepository;
    private PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository appUserRepository, AppGroupRepository appGroupRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appGroupRepository = appGroupRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public AppGroup addGroup(AppGroup group) {
        return appGroupRepository.save(group);
    }

    @Override
    public void addUserToGroup(String username, String name) {
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        Optional<AppGroup> group = appGroupRepository.findByGroupName(name);

        user.get().getGroups().add( group.get() );
        //appUserRepository.save( user ); not necessary thanks to the fact that the class is transactional
    }

    @Override
    public void removeUserToGroup(String username, String name) {
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        Optional<AppGroup> group = appGroupRepository.findByGroupName(name);

        user.get().getGroups().remove( group.get() );
    }

    @Override
    public AppUser loadUserByUsername(String username) {

        return appUserRepository.findByUsername(username).get();
    }

    @Override
    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public AppUser save(AppUser appUser) {
        String password = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(password));
//        appUser.setId(UUID.randomUUID().toString());
        return appUserRepository.save(appUser);
    }

    @Override
    public void delete(AppUser object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
