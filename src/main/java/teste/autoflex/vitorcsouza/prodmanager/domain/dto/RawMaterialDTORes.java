package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;

import java.util.UUID;

public record RawMaterialDTORes(UUID id, String name, String code, int stockQuantity) {
    public static RawMaterialDTORes fromEntity(RawMaterial rawMaterial) {
        return new RawMaterialDTORes(
                rawMaterial.getId(),
                rawMaterial.getName(),
                rawMaterial.getCode(),
                rawMaterial.getStockQuantity()
        );
    }
}
