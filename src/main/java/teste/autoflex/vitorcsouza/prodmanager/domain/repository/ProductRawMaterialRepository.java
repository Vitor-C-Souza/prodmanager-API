package teste.autoflex.vitorcsouza.prodmanager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;

import java.util.UUID;

public interface ProductRawMaterialRepository extends JpaRepository<RawMaterial, UUID> {
}
