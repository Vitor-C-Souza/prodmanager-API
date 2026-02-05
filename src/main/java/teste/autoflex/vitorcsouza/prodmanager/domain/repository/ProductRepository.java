package teste.autoflex.vitorcsouza.prodmanager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
