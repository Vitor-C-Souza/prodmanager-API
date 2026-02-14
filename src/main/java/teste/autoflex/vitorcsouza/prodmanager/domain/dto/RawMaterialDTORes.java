package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;

import java.io.Serializable;
import java.util.UUID;

@Schema(description = "Response object representing a raw material in the system")
public record RawMaterialDTORes(
        @Schema(description = "Unique identifier of the raw material",
                example = "7c9e6679-7425-40de-944b-e07fc1f90ae7", accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        @Schema(description = "Name of the raw material", example = "Aluminum Alloy")
        String name,
        @Schema(description = "Unique code for the material", example = "AL-5052-H32")
        String code,
        @Schema(description = "Current quantity available in stock", example = "250")
        int stockQuantity
) implements Serializable {
    private static final long serialVersionUID = 1L;
    public static RawMaterialDTORes fromEntity(RawMaterial rawMaterial) {
        return new RawMaterialDTORes(
                rawMaterial.getId(),
                rawMaterial.getName(),
                rawMaterial.getCode(),
                rawMaterial.getStockQuantity()
        );
    }
}
