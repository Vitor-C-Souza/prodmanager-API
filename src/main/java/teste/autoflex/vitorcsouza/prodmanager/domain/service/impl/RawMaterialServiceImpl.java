package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.RawMaterialDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.RawMaterialDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.RawMaterialService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RawMaterialServiceImpl implements RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    @Override
    public RawMaterialDTORes save(RawMaterialDTOReq dto) {
        RawMaterial rawMaterial = dto.toEntity();
        rawMaterialRepository.save(rawMaterial);
        return RawMaterialDTORes.fromEntity(rawMaterial);
    }

    @Override
    public List<RawMaterialDTORes> findAll() {

        List<RawMaterial> rawMaterials = rawMaterialRepository.findAll();

        return rawMaterials.stream().map(RawMaterialDTORes::fromEntity).toList();
    }

    @Override
    public RawMaterialDTORes findById(UUID id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Raw material not found"));

        return RawMaterialDTORes.fromEntity(rawMaterial);
    }

    @Override
    public void deleteById(UUID id) {

        if (rawMaterialRepository.existsById(id)) {
            rawMaterialRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Raw material not found");
        }
    }

    @Transactional
    public RawMaterialDTORes updateStock(UUID id, int quantity) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raw Material not found"));

        int newQuantity = rawMaterial.getStockQuantity() + quantity;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient stock for raw material: " + rawMaterial.getName());
        }

        rawMaterial.setStockQuantity(newQuantity);
        return RawMaterialDTORes.fromEntity(rawMaterialRepository.save(rawMaterial));
    }
}
