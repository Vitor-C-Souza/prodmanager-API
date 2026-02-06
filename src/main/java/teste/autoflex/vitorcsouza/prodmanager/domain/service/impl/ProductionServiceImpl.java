package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductionResultDTO;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.ProductRawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.ProductionService;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductionServiceImpl implements ProductionService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    @Transactional(readOnly = true)
    public List<ProductionResultDTO> calculateProduction() {
        List<Product> products = productRepository.findAll();
        List<ProductionResultDTO> result = new ArrayList<>();

        products.sort(
                Comparator.comparing(Product::getPrice).reversed()
        );
        Map<UUID, Integer> stockMap = new HashMap<>();

        for (RawMaterial rm : rawMaterialRepository.findAll()) {
            stockMap.put(rm.getId(), rm.getStockQuantity());
        }
        for (Product product : products) {

            if (product.getMaterials() == null || product.getMaterials().isEmpty()) {
                continue;
            }

            int maxProduction = Integer.MAX_VALUE;

            for (ProductRawMaterial prm : product.getMaterials()) {
                int available = stockMap.getOrDefault(
                        prm.getRawMaterial().getId(),
                        0
                );

                int needed = prm.getRequiredQuantity();

                int possible = available / needed;
                maxProduction = Math.min(maxProduction, possible);
            }

            for (ProductRawMaterial prm : product.getMaterials()) {
                UUID id = prm.getRawMaterial().getId();
                int used = maxProduction * prm.getRequiredQuantity();
                stockMap.put(id, stockMap.get(id) - used);
            }


            result.add(new ProductionResultDTO(
                    product.getName(),
                    maxProduction,
                    product.getPrice(),
                    product.getPrice().multiply(BigDecimal.valueOf(maxProduction))
            ));
        }

        return result;
    }
}
