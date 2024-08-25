package stee.monolith.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import stee.monolith.auth.dto.request.RegisterRequest;
import stee.monolith.auth.dto.response.RegisterResponse;
import stee.monolith.auth.service.RegisterService;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class RegisterController {
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest ){
        return new ResponseEntity<>( registerService.register( registerRequest ), HttpStatus.OK );
    }
}
