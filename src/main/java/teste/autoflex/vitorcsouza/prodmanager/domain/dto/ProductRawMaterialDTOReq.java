package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.ProductRawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;

import java.util.UUID;

public record ProductRawMaterialDTOReq(
        @NotNull(message = "Product id is required")
        UUID productId,

        @NotNull(message = "Raw material id is required")
        UUID rawMaterialId,

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
