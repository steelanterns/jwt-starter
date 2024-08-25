package stee.monolith.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by stee on 7/14/24.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createdBy;

    private String lastModifiedBy;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void prePersist(){
        setCreatedDate(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime());
    }

    @PreUpdate
    public void preUpdate(){
        setLastModifiedDate(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime());
    }

    public boolean isNew() {
        return this.id == null;
    }

    public void setUserForCurrentOperation(String username) {
        if (isNew()) {
            setCreatedBy(username);
        } else {
            setLastModifiedBy(username);
        }
    }
}
