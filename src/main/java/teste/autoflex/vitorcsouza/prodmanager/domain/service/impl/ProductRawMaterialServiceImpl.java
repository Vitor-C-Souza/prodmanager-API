package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductRawMaterialDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductRawMaterialDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.ProductRawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRawMaterialRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.ProductRawMaterialService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductRawMaterialServiceImpl implements ProductRawMaterialService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductRawMaterialRepository productRawMaterialRepository;

    @Override
    @Transactional
    public ProductRawMaterialDTORes save(ProductRawMaterialDTOReq dto) {
        Product product = productRepository.findById(dto.productId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId()).orElseThrow(() -> new EntityNotFoundException("Raw Material not found"));

        ProductRawMaterial productRawMaterial = dto.toEntity(product, rawMaterial);

        return ProductRawMaterialDTORes.fromEntity(productRawMaterial);
    }

    @Override
    public List<ProductRawMaterialDTORes> findAll() {
        return List.of();
    }

    @Override
    public ProductRawMaterialDTORes findById(UUID id) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
