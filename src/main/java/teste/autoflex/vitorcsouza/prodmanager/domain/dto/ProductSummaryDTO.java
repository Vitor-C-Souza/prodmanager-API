package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductSummaryDTO(
        UUID id,
        String name,
        String code,
        BigDecimal price
) {
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
