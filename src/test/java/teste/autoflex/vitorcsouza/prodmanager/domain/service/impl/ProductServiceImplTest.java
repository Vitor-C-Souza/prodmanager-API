package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void shouldSaveProduct() {
        // Arrange
        ProductDTOReq req =
                new ProductDTOReq("Product A", "P01", BigDecimal.valueOf(10));

        // Act
        ProductDTORes res = service.save(req);

        // Assert
        assertEquals("P01", res.code());
        assertEquals("Product A", res.name());
        assertEquals(BigDecimal.valueOf(10), res.price());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldFindAllProducts() {
        // Arrange
        Product p1 = Product.builder()
                .code("A")
                .name("Product 1")
                .price(BigDecimal.TEN)
                .build();

        Product p2 = Product.builder()
                .code("B")
                .name("Product 2")
                .price(BigDecimal.ONE)
                .build();

        when(productRepository.findAll())
                .thenReturn(List.of(p1, p2));

        // Act
        List<ProductDTORes> result = service.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).name());
        assertEquals("Product 2", result.get(1).name());
    }

    @Test
    void shouldFindById() {
        // Arrange
        UUID id = UUID.randomUUID();

        Product product = Product.builder()
                .id(id)
                .code("X")
                .name("Product X")
                .price(BigDecimal.TEN)
                .build();

        when(productRepository.findById(id))
                .thenReturn(Optional.of(product));

        // Act
        ProductDTORes res = service.findById(id);

        // Assert
        assertEquals("Product X", res.name());
    }

    @Test
    void shouldThrowWhenFindByIdNotExists() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(productRepository.findById(id))
                .thenReturn(Optional.empty());

        // Assert
        assertThrows(EntityNotFoundException.class,
                () -> service.findById(id));
    }

    @Test
    void shouldUpdateProduct() {
        // Arrange
        UUID id = UUID.randomUUID();

        Product existing = Product.builder()
                .id(id)
                .code("OLD")
                .name("TV")
                .price(BigDecimal.ONE)
                .build();

        ProductDTOReq req =
                new ProductDTOReq("LCD", "NEW", BigDecimal.TEN);

        when(productRepository.findById(id))
                .thenReturn(Optional.of(existing));

        // Act
        ProductDTORes res = service.update(id, req);

        // Assert
        assertEquals("NEW", res.code());
        assertEquals("LCD", res.name());
        assertEquals(BigDecimal.TEN, res.price());

        verify(productRepository).save(existing);
    }

    @Test
    void shouldThrowWhenUpdateNotExists() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(productRepository.findById(id))
                .thenReturn(Optional.empty());

        // Assert
        assertThrows(EntityNotFoundException.class,
                () -> service.update(id,
                        new ProductDTOReq("X", "Y", BigDecimal.ONE)));
    }

    @Test
    void shouldDeleteProduct() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(productRepository.existsById(id))
                .thenReturn(true);

        // Act
        service.deleteById(id);

        // Assert
        verify(productRepository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeleteNotExists() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(productRepository.existsById(id))
                .thenReturn(false);

        // Assert
        assertThrows(EntityNotFoundException.class,
                () -> service.deleteById(id));
    }
}