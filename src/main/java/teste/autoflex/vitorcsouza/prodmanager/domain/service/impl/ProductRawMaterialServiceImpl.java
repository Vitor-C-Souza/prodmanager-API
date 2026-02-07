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
import teste.autoflex.vitorcsouza.prodmanager.infra.exceptions.BusinessException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductRawMaterialServiceImpl implements ProductRawMaterialService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductRawMaterialRepository productRawMaterialRepository;

    @Override
    @Transactional
    public ProductRawMaterialDTORes link(UUID productId, ProductRawMaterialDTOReq productRawMaterialDTOReq) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(productRawMaterialDTOReq.rawMaterialId())
                .orElseThrow(() -> new EntityNotFoundException("Raw Material not found"));

        if (productRawMaterialRepository
                .existsByProductIdAndRawMaterialId(productId, productRawMaterialDTOReq.rawMaterialId())) {
            throw new BusinessException("Raw material already linked to product");
        }

        ProductRawMaterial entity = productRawMaterialDTOReq.toEntity(product, rawMaterial);

        productRawMaterialRepository.save(entity);

        return ProductRawMaterialDTORes.fromEntity(entity);
    }
}
