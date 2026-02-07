package teste.autoflex.vitorcsouza.prodmanager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
