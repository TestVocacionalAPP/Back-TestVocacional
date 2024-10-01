package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByUsuarioId(Long idUsuario);
    Optional<Test> findByIdAndUsuarioId(Long id, Long idUsuario);

}
