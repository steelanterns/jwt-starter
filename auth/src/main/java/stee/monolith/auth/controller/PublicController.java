package stee.monolith.auth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class PublicController {
    @GetMapping( path = "/" )
    public String publicAccess(){
        return "Public access";
    }
}
