package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

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
        RawMaterial rawMaterial = new RawMaterial(dto);
        repository.save(rawMaterial);
        return new RawMaterialDTORes(rawMaterial);
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
}
