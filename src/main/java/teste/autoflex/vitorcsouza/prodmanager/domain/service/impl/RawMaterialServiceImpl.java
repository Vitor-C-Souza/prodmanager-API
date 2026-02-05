package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.RawMaterialDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.RawMaterialDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.CrudBase;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RawMaterialServiceImpl implements CrudBase<RawMaterialDTORes, RawMaterialDTOReq> {

    private final RawMaterialRepository repository;

    @Override
    public RawMaterialDTORes save(RawMaterialDTOReq dto) {
        RawMaterial rawMaterial = dto.toEntity();
        repository.save(rawMaterial);
        return RawMaterialDTORes.fromEntity(rawMaterial);
    }

    @Override
    public List<RawMaterialDTORes> findAll() {
        return List.of();
    }

    @Override
    public RawMaterialDTORes findById(UUID id) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Transactional
    public RawMaterialDTORes updateStock(UUID id, int quantity) {
        RawMaterial rawMaterial = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raw Material not found"));

        int newQuantity = rawMaterial.getStockQuantity() + quantity;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient stock for raw material: " + rawMaterial.getName());
        }

        rawMaterial.setStockQuantity(newQuantity);
        return RawMaterialDTORes.fromEntity(repository.save(rawMaterial));
    }
}
