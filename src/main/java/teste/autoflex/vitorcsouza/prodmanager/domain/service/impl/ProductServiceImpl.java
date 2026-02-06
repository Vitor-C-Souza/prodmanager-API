package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.ProductService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDTORes save(ProductDTOReq dto) {
        Product product = dto.toEntity();

        productRepository.save(product);

        return ProductDTORes.fromEntity(product);
    }

    @Override
    public List<ProductDTORes> findAll() {
        return List.of();
    }

    @Override
    public ProductDTORes findById(UUID id) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
