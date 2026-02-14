package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Summary representation of a product for optimized listings")
public record ProductSummaryDTO(

        @Schema(description = "Unique identifier of the product",
                example = "550e8400-e29b-41d4-a716-446655440000",
                accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        @Schema(description = "Commercial name of the product",
                example = "G-Pro Mechanical Keyboard")
        String name,

        @Schema(description = "Unique product SKU or barcode",
                example = "KB-990-MK")
        String code,

        @Schema(description = "Unit selling price of the product",
                example = "129.90")
        BigDecimal price

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public ProductSummaryDTO(Product product) {
        this(product.getId(), product.getName(), product.getCode(), product.getPrice());
    }

    public static ProductSummaryDTO fromEntity(Product product) {
        return new ProductSummaryDTO(
                product.getId(),
                product.getName(),
                product.getCode(),
                product.getPrice()
        );
    }
}