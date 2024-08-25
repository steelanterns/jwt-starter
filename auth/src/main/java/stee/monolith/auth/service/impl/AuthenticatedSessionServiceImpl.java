package stee.monolith.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stee.monolith.auth.dto.request.LoginRequest;
import stee.monolith.auth.dto.response.LoginResponse;
import stee.monolith.auth.entity.AppUser;
import stee.monolith.auth.repository.AppUserRepository;
import stee.monolith.auth.service.AuthenticatedSessionService;
import stee.monolith.auth.service.JwtService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticatedSessionServiceImpl implements AuthenticatedSessionService {
    private JwtService jwtService;
    private AppUserRepository appUserRepository;
    @Override
    public LoginResponse login( LoginRequest loginRequest ) {
        var token = jwtService.authenticate(
                loginRequest.getGrantType(),
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        Optional<AppUser> appUser = appUserRepository.findByUsername(loginRequest.getUsername());
//        if( !appUser.isPresent() )
//            throw new AuthenticateException( "username or password invalid" );
        return new LoginResponse( appUser.get(), token );
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        var token = jwtService.refreshToken( refreshToken );
        return new LoginResponse( null, token );
    }
}
