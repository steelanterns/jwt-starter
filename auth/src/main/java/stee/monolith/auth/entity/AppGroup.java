package stee.monolith.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import stee.monolith.common.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class AppGroup extends BaseEntity {

    @Column(unique = true)
    private String groupName;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_authorities",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities = new HashSet<>();

    //Not necessary to have bidirectional relationchip. we have another way to get users on a specific group.
}
