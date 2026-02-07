package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductionResultDTO;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.ProductRawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductionServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private ProductionServiceImpl productionService;

    @Test
    void shouldCalculateMaxProductionCorrectly() {
        // Arrange
        RawMaterial rm = RawMaterial.builder()
                .id(UUID.randomUUID())
                .stockQuantity(100)
                .build();

        Product product = Product.builder()
                .name("Produto A")
                .price(BigDecimal.valueOf(10))
                .build();

        ProductRawMaterial prm = ProductRawMaterial.builder()
                .product(product)
                .rawMaterial(rm)
                .requiredQuantity(10)
                .build();

        product.setMaterials(List.of(prm));

        when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(product)));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(rm));

        // Act
        List<ProductionResultDTO> result = productionService.calculateProduction();

        // Assert
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).maxProduction());
        assertEquals(BigDecimal.valueOf(100), result.get(0).multiply());
    }

    @Test
    void shouldUseLowestAvailableRawMaterial() {
        // arrange
        RawMaterial rm1 = RawMaterial.builder()
                .id(UUID.randomUUID())
                .stockQuantity(100)
                .build();

        RawMaterial rm2 = RawMaterial.builder()
                .id(UUID.randomUUID())
                .stockQuantity(30)
                .build();

        Product product = Product.builder()
                .name("Produto B")
                .price(BigDecimal.valueOf(20))
                .build();

        ProductRawMaterial p1 = ProductRawMaterial.builder()
                .product(product)
                .rawMaterial(rm1)
                .requiredQuantity(10)
                .build();

        ProductRawMaterial p2 = ProductRawMaterial.builder()
                .product(product)
                .rawMaterial(rm2)
                .requiredQuantity(5)
                .build();

        product.setMaterials(new ArrayList<>(List.of(p1, p2)));

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(rawMaterialRepository.findAll()).thenReturn(new ArrayList<>(List.of(rm1, rm2)));

        //act
        List<ProductionResultDTO> result = productionService.calculateProduction();

        //assert
        assertEquals(6, result.get(0).maxProduction());
    }

}