package stee.monolith.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import stee.monolith.auth.entity.AppUser;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponse {

    private AppUser user;
    private Map<String, String> jwt;

}
