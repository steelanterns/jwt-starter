package stee.monolith.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Base Data Transfer Object (DTO) class for common fields.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {

    private Long id;

    private String createdBy;

    private String lastModifiedBy;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedDate;
}
