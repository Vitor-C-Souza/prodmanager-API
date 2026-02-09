package teste.autoflex.vitorcsouza.prodmanager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.ProductRawMaterial;

import java.util.UUID;

public interface ProductRawMaterialRepository extends JpaRepository<ProductRawMaterial, UUID> {
    boolean existsByProductIdAndRawMaterialId(UUID productId, UUID productRawMaterialDTOReq);

    void deleteByProductIdAndRawMaterialId(UUID productId, UUID rawMaterialId);
}
