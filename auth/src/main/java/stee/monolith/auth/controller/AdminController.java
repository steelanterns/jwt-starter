package stee.monolith.auth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AdminController {
    @GetMapping( path = "/admin" )
    public String reservedAccess(){
        return "Reserved access";
    }
}
