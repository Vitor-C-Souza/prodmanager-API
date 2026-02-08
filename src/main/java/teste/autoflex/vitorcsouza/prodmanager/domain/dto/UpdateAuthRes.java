package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.User;

import java.util.UUID;

@Schema(description = "Response object containing updated user account details")
public record UpdateAuthRes(
        @Schema(description = "Unique identifier of the user", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Updated username", example = "vitor.souza.new")
        String username,

        @Schema(description = "The encrypted password (hashed)", example = "$2a$10$X5L...")
        String password,

        @Schema(description = "Updated email address", example = "new.email@prodmanager.com")
        String email
) {
    public static UpdateAuthRes fromEntity(User user) {
        return new UpdateAuthRes(
                user.getId(),
                user.getDisplayUsername(),
                user.getPassword(),
                user.getEmail()
        );
    }
}