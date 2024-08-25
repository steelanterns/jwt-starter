package stee.monolith.auth.service;


import stee.monolith.auth.dto.request.RegisterRequest;
import stee.monolith.auth.dto.response.RegisterResponse;

public interface RegisterService {
    public RegisterResponse register(RegisterRequest registerRequest );

}
