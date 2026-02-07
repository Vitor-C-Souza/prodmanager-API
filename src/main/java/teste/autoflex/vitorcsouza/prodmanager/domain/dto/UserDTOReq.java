package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Role;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.User;

@Schema(description = "Data required to register a new user")
public record UserDTOReq(
        @Schema(description = "Unique username for the account", example = "vitorsouza")
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @Schema(description = "Strong password containing uppercase, lowercase and numbers", example = "Admin123!")
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be at least 8 characters long")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
                message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, and one number"
        )
        String password,

        @Schema(description = "Valid email address for login", example = "vitor@prodmanager.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @Schema(description = "User access role", example = "ADMIN")
        @NotNull(message = "Role is required")
        Role role
) {
    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .role(this.role)
                .build();
    }
}