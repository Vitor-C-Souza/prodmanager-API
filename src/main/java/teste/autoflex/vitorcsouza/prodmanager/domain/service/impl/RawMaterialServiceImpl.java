package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    @CacheEvict(value = {"productionCalculation", "rawMaterialsList"}, allEntries = true)
    public RawMaterialDTORes save(RawMaterialDTOReq dto) {
        RawMaterial rawMaterial = dto.toEntity();
        rawMaterialRepository.save(rawMaterial);
        return RawMaterialDTORes.fromEntity(rawMaterial);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "rawMaterialsList")
    public List<RawMaterialDTORes> findAll() {

        List<RawMaterial> rawMaterials = rawMaterialRepository.findAll();

        return rawMaterials.stream().map(RawMaterialDTORes::fromEntity).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RawMaterialDTORes findById(UUID id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Raw material not found"));

        return RawMaterialDTORes.fromEntity(rawMaterial);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"productionCalculation", "rawMaterialsList"}, allEntries = true)
    public RawMaterialDTORes update(UUID id, RawMaterialDTOReq dto) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Raw material not found"));

        rawMaterial.setCode(dto.code());
        rawMaterial.setName(dto.name());
        rawMaterialRepository.save(rawMaterial);

        return RawMaterialDTORes.fromEntity(rawMaterial);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"productionCalculation", "rawMaterialsList"}, allEntries = true)
    public void deleteById(UUID id) {

        if (rawMaterialRepository.existsById(id)) {
            rawMaterialRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Raw material not found");
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"productionCalculation", "rawMaterialsList"}, allEntries = true)
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
