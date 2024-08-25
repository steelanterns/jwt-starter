package stee.monolith.auth.service;

import stee.monolith.auth.entity.AppGroup;
import stee.monolith.auth.entity.AppUser;
import stee.monolith.auth.entity.Authority;

public interface AppUserService extends CrudService<AppUser, Long> {
    AppGroup addGroup (AppGroup group );
    void addUserToGroup( String username, String name );
    void removeUserToGroup( String username, String name );
    AppUser loadUserByUsername( String username );
}
