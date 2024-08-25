package stee.monolith.auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import stee.monolith.auth.entity.AppGroup;
import stee.monolith.auth.entity.AppUser;
import stee.monolith.auth.entity.Authority;
import stee.monolith.auth.repository.AppGroupRepository;
import stee.monolith.auth.repository.AppUserRepository;
import stee.monolith.auth.repository.AuthorityRepository;
import stee.monolith.common.utils.UserType;

@Component
public class DataInitializer {

    private final AppUserRepository userRepository;

    private final AppGroupRepository groupRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AppUserRepository userRepository, AppGroupRepository groupRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            // Create authorities if they do not exist
            Authority readAuth = authorityRepository.findByAuthority("READ")
                    .orElseGet(() -> authorityRepository.save(new Authority("READ", "Read privileges")));
            Authority writeAuth = authorityRepository.findByAuthority("WRITE")
                    .orElseGet(() -> authorityRepository.save(new Authority("WRITE", "Write privileges")));
            Authority deleteAuth = authorityRepository.findByAuthority("DELETE")
                    .orElseGet(() -> authorityRepository.save(new Authority("DELETE", "Delete privileges")));
            Authority downloadAuth = authorityRepository.findByAuthority("DOWNLOAD")
                    .orElseGet(() -> authorityRepository.save(new Authority("DOWNLOAD", "Download privileges")));

            // Create admin group if it does not exist
            AppGroup adminGroup = groupRepository.findByGroupName("ADMIN")
                    .orElseGet(() -> {
                        AppGroup newAdminGroup = new AppGroup();
                        newAdminGroup.setGroupName("ADMIN");
                        newAdminGroup.setDescription("Admin group");
                        newAdminGroup.getAuthorities().add(readAuth);
                        newAdminGroup.getAuthorities().add(writeAuth);
                        newAdminGroup.getAuthorities().add(deleteAuth);
                        newAdminGroup.getAuthorities().add(downloadAuth);
                        return groupRepository.save(newAdminGroup);
                    });

            // Create guest group if it does not exist
            AppGroup guestGroup = groupRepository.findByGroupName("GUEST")
                    .orElseGet(() -> {
                        AppGroup newGuestGroup = new AppGroup();
                        newGuestGroup.setGroupName("GUEST");
                        newGuestGroup.setDescription("Guest group");
                        newGuestGroup.getAuthorities().add(readAuth);
                        return groupRepository.save(newGuestGroup);
                    });

            // Create admin user if it does not exist
            if (userRepository.findByUsername("admin").isEmpty()) {
                AppUser adminUser = new AppUser();
                adminUser.setUsername("admin");
                adminUser.setEmail("admin@example.com");
                adminUser.setType(UserType.SYSTEM.getDisplayName());
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.getGroups().add(adminGroup);
                userRepository.save(adminUser);
            }
        };
    }

}
