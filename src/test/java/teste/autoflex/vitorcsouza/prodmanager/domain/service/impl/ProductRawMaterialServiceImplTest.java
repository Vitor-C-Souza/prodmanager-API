package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductRawMaterialDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductRawMaterialDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRawMaterialRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRawMaterialServiceImplTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    RawMaterialRepository rawMaterialRepository;

    @Mock
    ProductRawMaterialRepository productRawMaterialRepository;

    @InjectMocks
    ProductRawMaterialServiceImpl service;

    @Test
    void shouldLinkProductWithRawMaterial() {
        // Arrange
        UUID productId = UUID.randomUUID();
        UUID rawMaterialId = UUID.randomUUID();

        Product product = Product.builder().id(productId).name("Product X").build();

        RawMaterial rawMaterial = RawMaterial.builder().id(rawMaterialId).name("Steel").build();

        ProductRawMaterialDTOReq req = new ProductRawMaterialDTOReq(rawMaterialId, 5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(rawMaterialRepository.findById(rawMaterialId)).thenReturn(Optional.of(rawMaterial));

        // Act
        ProductRawMaterialDTORes res = service.link(productId, req);

        // Assert
        assertEquals("Product X", res.product());
        assertEquals("Steel", res.rawMaterialName());
        assertEquals(5, res.requiredQuantity());
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        // Arrange
        UUID productId = UUID.randomUUID();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ProductRawMaterialDTOReq req = new ProductRawMaterialDTOReq(UUID.randomUUID(), 5);

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> service.link(productId, req));

        verify(productRawMaterialRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenRawMaterialNotFound() {
        // Arrange
        UUID productId = UUID.randomUUID();
        UUID rawMaterialId = UUID.randomUUID();

        Product product = Product.builder().id(productId).build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(rawMaterialRepository.findById(rawMaterialId)).thenReturn(Optional.empty());

        ProductRawMaterialDTOReq req = new ProductRawMaterialDTOReq(rawMaterialId, 5);

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> service.link(productId, req));

        verify(productRawMaterialRepository, never()).save(any());
    }

}