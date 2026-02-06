package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public List<ProductDTORes> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDTORes::fromEntity).toList();
    }

    @Override
    @Transactional
    public ProductDTORes findById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return ProductDTORes.fromEntity(product);
    }

    @Override
    public void deleteById(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Product not found");
        }
    }
}
