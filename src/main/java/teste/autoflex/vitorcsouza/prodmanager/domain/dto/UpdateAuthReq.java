package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for updating user account information. All fields are optional.")
public record UpdateAuthReq(
        @Schema(description = "New username for the account", example = "vitor.souza.new")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @Schema(description = "New email address", example = "new.email@prodmanager.com")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "New strong password", example = "NewStrongPass123!")
        @Size(min = 8, max = 100, message = "Password must be at least 8 characters long")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
                message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, and one number"
        )
        String password
) {
}