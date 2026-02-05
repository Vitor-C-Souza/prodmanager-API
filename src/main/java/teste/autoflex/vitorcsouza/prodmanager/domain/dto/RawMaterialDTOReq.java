package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RawMaterialDTOReq(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Code is required")
        String code,

        @NotNull(message = "Stock quantity is required")
        @PositiveOrZero(message = "Stock quantity cannot be negative")
        int stockQuantity
) {
}
