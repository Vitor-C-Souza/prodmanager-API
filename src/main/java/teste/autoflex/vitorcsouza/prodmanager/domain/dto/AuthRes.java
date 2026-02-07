package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response containing the access token after successful authentication")
public record AuthRes(
        @Schema(description = "JWT (JSON Web Token) used for authorizing subsequent requests",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {
}