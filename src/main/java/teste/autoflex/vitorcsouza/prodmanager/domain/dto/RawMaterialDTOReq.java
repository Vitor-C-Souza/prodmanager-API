package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;

public record RawMaterialDTOReq(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Code is required")
        String code,

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
