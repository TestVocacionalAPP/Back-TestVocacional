package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.HistorialTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistorialTestRepository extends JpaRepository<HistorialTest, Long> {
    Optional<HistorialTest> findByUsuarioIdAndTestId(Long idUsuario, Long idTest);
}
