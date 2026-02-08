package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.User;

import java.util.UUID;

@Schema(description = "Response object representing a registered user")
public record UserDTORes(
        @Schema(description = "Unique identifier of the user", example = "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6")
        UUID id,

        @Schema(description = "Account username", example = "vitorsouza")
        String username,

        @Schema(description = "Hashed password for security verification", example = "$2y$12$KIs...")
        String password,

        @Schema(description = "Registered user email", example = "vitor@prodmanager.com")
        String email
) {

    public static UserDTORes fromEntity(User user) {
        return new UserDTORes(
                user.getId() != null ? user.getId() : null,
                user.getDisplayUsername(),
                user.getPassword(),
                user.getEmail()
        );
    }
}