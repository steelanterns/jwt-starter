package stee.monolith.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stee.monolith.auth.dto.request.RegisterRequest;
import stee.monolith.auth.dto.response.RegisterResponse;
import stee.monolith.auth.repository.AppGroupRepository;
import stee.monolith.auth.repository.AppUserRepository;
import stee.monolith.auth.service.RegisterService;

@Service
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private AppGroupRepository appGroupRepository;
    private AppUserRepository appUserRepository;
    @Override
    public RegisterResponse register(RegisterRequest registerRequest ) {
        return null;
    }
}
