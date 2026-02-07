package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credentials required for user authentication")
public record AuthReq(
        @Schema(description = "User's registered email address", example = "vitor@prodmanager.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "User's secret password", example = "Admin123!")
        @NotBlank(message = "Password cannot be empty")
        String password
) {
}
