package stee.monolith.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stee.monolith.auth.dto.request.AuthorityToUserRequest;
import stee.monolith.auth.dto.request.GroupOfUserRequest;
import stee.monolith.auth.entity.AppGroup;
import stee.monolith.auth.entity.AppUser;
import stee.monolith.auth.entity.Authority;
import stee.monolith.auth.service.AppUserService;

import java.util.List;
/**
 * Created by .stee on 09/22/2023
 */
@RestController
public class AppUserRestController {

    private AppUserService appUserService;

    public AppUserRestController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    //@CrossOrigin(origins = "http://localhost:8082")
    @GetMapping(path = "/users")
    @PreAuthorize("hasAnyAuthority('SCOPE_READ')") // this is how to Autorize
    public List<AppUser> users(){
        return appUserService.findAll();
    }

    @PostMapping(path = "users")
    public AppUser save(@RequestBody AppUser appUser){
        return appUserService.save( appUser );
    }

    @PostMapping(path = "/roles")
    public AppGroup saveGroup(@RequestBody AppGroup appGroup){
        return appUserService.addGroup( appGroup );
    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody GroupOfUserRequest groupOfUserRequest){
        appUserService.addUserToGroup( groupOfUserRequest.username(), groupOfUserRequest.authority() );
    }
}
