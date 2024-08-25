package stee.monolith.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stee.monolith.common.entity.BaseEntity;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority extends BaseEntity {

    @Column(unique = true)
    private String authority;

    private String description;

    //Not necessary to have bidirectional relationchip. we have another way to get users on a specific group.
}
