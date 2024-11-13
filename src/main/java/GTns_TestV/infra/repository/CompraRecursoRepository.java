package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.CompraRecurso;
import GTns_TestV.model.entity.Recurso;
import GTns_TestV.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRecursoRepository extends JpaRepository<CompraRecurso, Long> {
    boolean existsByUsuarioIdAndRecursoId(Long usuarioId, Long recursoId);
    List<CompraRecurso> findByUsuarioId(Long usuarioId);
}
