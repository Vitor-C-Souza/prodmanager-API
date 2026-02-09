package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.ProductRawMaterial;

import java.util.UUID;

@Schema(description = "Response object showing the link between a product and its required raw material")
public record ProductRawMaterialDTORes(
        @Schema(description = "Unique identifier of the association",
                example = "550e8400-e29b-41d4-a716-446655440000", accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        @Schema(description = "The product", example = "Mechanical Keyboard G-Pro")
        ProductDTORes product,
        @Schema(description = "Name of the raw material assigned to this product", example = "ABS Plastic")
        RawMaterialDTORes rawMaterial,
        @Schema(description = "Quantity of this specific material needed to produce one unit of the product", example = "1")
        int requiredQuantity
) {
    public ProductRawMaterialDTORes(ProductRawMaterial entity) {
        this(
                entity.getId(),
                ProductDTORes.fromEntity(entity.getProduct()),
                RawMaterialDTORes.fromEntity(entity.getRawMaterial()),
                entity.getRequiredQuantity()
        );
    }

    public static ProductRawMaterialDTORes fromEntity(ProductRawMaterial entity) {
        return new ProductRawMaterialDTORes(
                entity.getId(),
                ProductDTORes.fromEntity(entity.getProduct()),
                RawMaterialDTORes.fromEntity(entity.getRawMaterial()),
                entity.getRequiredQuantity()
        );
    }
}
