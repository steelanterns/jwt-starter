package stee.monolith.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import stee.monolith.auth.dto.request.LoginRequest;
import stee.monolith.auth.dto.response.LoginResponse;
import stee.monolith.auth.service.AuthenticatedSessionService;

@RestController
//@CrossOrigin("*")
@AllArgsConstructor
public class AuthenticatedSessionController {
    private AuthenticatedSessionService authenticatedSessionService;
    /**
     * Attempt to authenticate a new session.
     *
     */
    @PostMapping(value ="/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest )
    {
        return new ResponseEntity<>( authenticatedSessionService.login( loginRequest ), HttpStatus.OK );
    }

    /**
     * Attempt to authenticate a new session.
     *
     */
    @PostMapping(value ="/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody String refreshToken )
    {
        return new ResponseEntity<>( authenticatedSessionService.refreshToken( refreshToken ), HttpStatus.OK );
    }

    /**
     * Destroy an authenticated session.
     *
     */
//    public ResponseEntity<LogoutResponse> destroy()
//    {
//
//        return null;
//    }
}
