package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import teste.autoflex.vitorcsouza.prodmanager.domain.model.ProductRawMaterial;

import java.util.UUID;

public record ProductRawMaterialDTORes(
        UUID id,
        String productName,
        String rawMaterialName,
        int requiredQuantity
) {
    public static ProductRawMaterialDTORes fromEntity(ProductRawMaterial entity) {
        return new ProductRawMaterialDTORes(
                entity.getId(),
                entity.getProduct().getName(),
                entity.getRawMaterial().getName(),
                entity.getRequiredQuantity()
        );
    }
}
