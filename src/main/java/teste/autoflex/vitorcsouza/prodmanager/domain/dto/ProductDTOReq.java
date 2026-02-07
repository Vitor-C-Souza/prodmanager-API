package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;

import java.math.BigDecimal;

@Schema(description = "Data required to create or update a product")
public record ProductDTOReq(
        @Schema(description = "Full name of the product", example = "Mechanical Keyboard G-Pro")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Unique alphanumeric product code", example = "KB-990-MK")
        @NotBlank(message = "Code is required")
        String code,

        @Schema(description = "Unit price of the product", example = "599.90")
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price
) {
    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .code(this.code)
                .price(this.price)
                .build();
    }
}
