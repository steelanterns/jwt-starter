package stee.monolith.auth.dto.request;

import java.util.List;

public record AppUserRequest(
        String username,
        String password,
        String email,
        String fullName,
        List<String> groups
) {
}
