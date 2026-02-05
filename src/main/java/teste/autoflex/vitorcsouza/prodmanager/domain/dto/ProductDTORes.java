package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTORes(
        UUID id,
        String name,
        String code,
        BigDecimal price
) {
    public static ProductDTORes fromEntity(Product product) {
        return new ProductDTORes(
                product.getId(),
                product.getName(),
                product.getCode(),
                product.getPrice()
        );
    }
}
