package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;

@Schema(description = "Data required to create or update a raw material")
public record RawMaterialDTOReq(
        @Schema(description = "Name of the raw material", example = "Stainless Steel Sheet")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Unique code for the material", example = "SS-404-MAT")
        @NotBlank(message = "Code is required")
        String code,

        @Schema(description = "Current quantity available in stock", example = "150")
        @NotNull(message = "Stock quantity is required")
        @PositiveOrZero(message = "Stock quantity cannot be negative")
        int stockQuantity
) {

    public RawMaterial toEntity() {
        return RawMaterial.builder()
                .name(this.name)
                .code(this.code)
                .stockQuantity(this.stockQuantity)
                .build();
    }
}
