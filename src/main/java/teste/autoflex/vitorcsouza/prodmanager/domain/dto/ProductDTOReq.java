package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductDTOReq(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Code is required")
        String code,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price
) {
}
