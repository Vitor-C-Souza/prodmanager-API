package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.ProductRawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;

import java.util.UUID;

@Schema(description = "Data required to link a raw material to a product")
public record ProductRawMaterialDTOReq(

        @Schema(description = "Unique identifier of the raw material",
                example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull(message = "Raw material id is required")
        UUID rawMaterialId,

        @Schema(description = "Amount of material needed for the product",
                example = "15")
        @Positive(message = "Quantity must be greater than zero")
        int requiredQuantity
) {

    public ProductRawMaterial toEntity(Product product, RawMaterial rawMaterial) {
        return ProductRawMaterial.builder()
                .product(product)
                .rawMaterial(rawMaterial)
                .requiredQuantity(this.requiredQuantity)
                .build();
    }
}
