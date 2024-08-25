package stee.monolith.auth.dto.request;


import lombok.Getter;
import lombok.Setter;
import stee.monolith.common.utils.GrantType;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
    private GrantType grantType;
    private boolean withRefreshToken;
    private String refreshToken;

}
