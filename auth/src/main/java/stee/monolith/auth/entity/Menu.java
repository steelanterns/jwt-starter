package stee.monolith.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stee.monolith.common.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "menus")
public class Menu extends BaseEntity {
    private Long parentId;
    private Long position;
    private String name;
    private String icon;
    private String url;
    private String description;
    private String type;
    private String permission;
    private String componentPath;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppGroup> groups = new HashSet<>();
}
