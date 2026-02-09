package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "Response object representing a product in the system")
public record ProductDTORes(
        @Schema(description = "Unique identifier of the product",
                example = "550e8400-e29b-41d4-a716-446655440000", accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        @Schema(description = "Name of the product", example = "Mechanical Keyboard G-Pro")
        String name,
        @Schema(description = "Unique product code", example = "KB-990-MK")
        String code,
        @Schema(description = "Unit price of the product", example = "599.90")
        BigDecimal price,
        @Schema(description = "List of raw materials that compose this product")
        @JsonIgnoreProperties("product")
        List<ProductRawMaterialDTORes> productRawMaterial
) {
    public static ProductDTORes fromEntity(Product product) {
        return new ProductDTORes(
                product.getId(),
                product.getName(),
                product.getCode(),
                product.getPrice(),
                product.getMaterials().stream().map(ProductRawMaterialDTORes::new).toList()
        );
    }
}
