package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.RawMaterialDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.RawMaterialDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RawMaterialServiceImplTest {

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private RawMaterialServiceImpl service;

    @Test
    void shouldSaveRawMaterial() {
        // Arrange
        RawMaterialDTOReq req =
                new RawMaterialDTOReq("Steel", "RM01", 100);

        // Act
        RawMaterialDTORes res = service.save(req);

        // Assert
        assertEquals("RM01", res.code());
        assertEquals("Steel", res.name());
        assertEquals(100, res.stockQuantity());

        verify(rawMaterialRepository).save(any(RawMaterial.class));
    }

    @Test
    void shouldFindAllRawMaterials() {
        // Arrange
        RawMaterial rm1 = RawMaterial.builder()
                .code("A")
                .name("Iron")
                .stockQuantity(50)
                .build();

        RawMaterial rm2 = RawMaterial.builder()
                .code("B")
                .name("Copper")
                .stockQuantity(30)
                .build();

        when(rawMaterialRepository.findAll())
                .thenReturn(List.of(rm1, rm2));

        // Act
        List<RawMaterialDTORes> result = service.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Iron", result.get(0).name());
        assertEquals("Copper", result.get(1).name());
    }

    @Test
    void shouldFindById() {
        // Arrange
        UUID id = UUID.randomUUID();

        RawMaterial rm = RawMaterial.builder()
                .id(id)
                .code("X")
                .name("Plastic")
                .stockQuantity(20)
                .build();

        when(rawMaterialRepository.findById(id))
                .thenReturn(Optional.of(rm));

        // Act
        RawMaterialDTORes res = service.findById(id);

        // Assert
        assertEquals("Plastic", res.name());
    }

    @Test
    void shouldThrowWhenFindByIdNotExists() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(rawMaterialRepository.findById(id))
                .thenReturn(Optional.empty());

        // Assert
        assertThrows(EntityNotFoundException.class,
                () -> service.findById(id));
    }

    @Test
    void shouldUpdateRawMaterial() {
        // Arrange
        UUID id = UUID.randomUUID();

        RawMaterial existing = RawMaterial.builder()
                .id(id)
                .code("OLD")
                .name("Old")
                .stockQuantity(10)
                .build();

        RawMaterialDTOReq req =
                new RawMaterialDTOReq("New", "NEW", 10);

        when(rawMaterialRepository.findById(id))
                .thenReturn(Optional.of(existing));

        // Act
        RawMaterialDTORes res = service.update(id, req);

        // Assert
        assertEquals("NEW", res.code());
        assertEquals("New", res.name());

        verify(rawMaterialRepository).save(existing);
    }

    @Test
    void shouldThrowWhenUpdateNotExists() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(rawMaterialRepository.findById(id))
                .thenReturn(Optional.empty());

        // Assert
        assertThrows(EntityNotFoundException.class,
                () -> service.update(id,
                        new RawMaterialDTOReq("X", "Y", 1)));
    }

    @Test
    void shouldDeleteRawMaterial() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(rawMaterialRepository.existsById(id))
                .thenReturn(true);

        // Act
        service.deleteById(id);

        // Assert
        verify(rawMaterialRepository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeleteNotExists() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(rawMaterialRepository.existsById(id))
                .thenReturn(false);

        // Assert
        assertThrows(EntityNotFoundException.class,
                () -> service.deleteById(id));
    }

    @Test
    void shouldIncreaseStock() {
        // Arrange
        UUID id = UUID.randomUUID();

        RawMaterial rm = RawMaterial.builder()
                .id(id)
                .name("Steel")
                .stockQuantity(10)
                .build();

        when(rawMaterialRepository.findById(id))
                .thenReturn(Optional.of(rm));
        when(rawMaterialRepository.save(any()))
                .thenReturn(rm);

        // Act
        RawMaterialDTORes res = service.updateStock(id, 5);

        // Assert
        assertEquals(15, res.stockQuantity());
    }

    @Test
    void shouldDecreaseStock() {
        // Arrange
        UUID id = UUID.randomUUID();

        RawMaterial rm = RawMaterial.builder()
                .id(id)
                .name("Steel")
                .stockQuantity(10)
                .build();

        when(rawMaterialRepository.findById(id))
                .thenReturn(Optional.of(rm));
        when(rawMaterialRepository.save(any()))
                .thenReturn(rm);

        // Act
        RawMaterialDTORes res = service.updateStock(id, -3);

        // Assert
        assertEquals(7, res.stockQuantity());
    }

    @Test
    void shouldThrowWhenStockBecomesNegative() {
        // Arrange
        UUID id = UUID.randomUUID();

        RawMaterial rm = RawMaterial.builder()
                .id(id)
                .name("Steel")
                .stockQuantity(5)
                .build();

        when(rawMaterialRepository.findById(id))
                .thenReturn(Optional.of(rm));

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> service.updateStock(id, -10));
    }

    @Test
    void shouldThrowWhenUpdateStockNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(rawMaterialRepository.findById(id))
                .thenReturn(Optional.empty());

        // Assert
        assertThrows(EntityNotFoundException.class,
                () -> service.updateStock(id, 5));
    }
}