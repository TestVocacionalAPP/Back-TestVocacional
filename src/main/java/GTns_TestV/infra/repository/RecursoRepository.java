package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    List<Recurso> findByTituloContaining(String titulo);

    List<Recurso> findByDescripcionContaining(String descripcion);

    List<Recurso> findByTituloContainingAndDescripcionContaining(String titulo, String descripcion);
}
