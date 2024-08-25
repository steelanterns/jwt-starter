package stee.monolith.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import stee.monolith.common.entity.BaseEntity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by stee on 7/14/24.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_users")
public class AppUser extends BaseEntity implements UserDetails {

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<AppGroup> groups = new HashSet<>();
    //String[] roles = user.getAuthorities().stream().map( u -> u.getAuthority() ).toArray( String[]::new );
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return groups.stream()
                .flatMap(group -> group.getAuthorities().stream())
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toSet());
    }
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> mappedAuthorities = new HashSet<>();
//        getGroups().forEach( group -> {
//            mappedAuthorities.add( new SimpleGrantedAuthority( group.getGroupName() ) );
//        });
//        return mappedAuthorities;
//    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
