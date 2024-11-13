package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.Comentario;
import GTns_TestV.model.entity.Experto;
import GTns_TestV.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findAllByExperto(Experto experto);
}
