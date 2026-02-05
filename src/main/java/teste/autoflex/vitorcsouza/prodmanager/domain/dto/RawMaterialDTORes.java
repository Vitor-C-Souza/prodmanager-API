package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;

import java.util.UUID;

public record RawMaterialDTORes(UUID ID, String name, String code, int stockQuantity) {
    public RawMaterialDTORes(RawMaterial rawMaterial) {
        this(rawMaterial.getId(), rawMaterial.getName(), rawMaterial.getCode(), rawMaterial.getStockQuantity());
    }
}
