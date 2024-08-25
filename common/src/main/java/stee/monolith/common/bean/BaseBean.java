package stee.monolith.common.bean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Base interface for beans, defining common properties.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface BaseBean extends Serializable {
    String getCreatedBy();
    String getLastModifiedBy();
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime getCreatedDate();
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime getLastModifiedDate();
}
