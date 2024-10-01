package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.Asesoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsesoriaRepository extends JpaRepository<Asesoria, Long> {
    List<Asesoria> findByExpertoId(Long expertoId);
    List<Asesoria> findByUsuarioId(Long usuarioId);
}
