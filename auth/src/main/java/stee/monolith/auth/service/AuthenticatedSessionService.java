package stee.monolith.auth.service;


import stee.monolith.auth.dto.request.LoginRequest;
import stee.monolith.auth.dto.response.LoginResponse;

public interface AuthenticatedSessionService {
    LoginResponse login( LoginRequest loginRequest );
    LoginResponse refreshToken( String refreshToken );
}
