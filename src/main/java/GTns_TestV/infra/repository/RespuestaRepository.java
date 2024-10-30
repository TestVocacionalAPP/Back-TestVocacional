package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    List<Respuesta> findByUsuarioIdAndTestId(Long usuarioId, Long testId);

    List<Respuesta> findByHistorialTestId(Long historialTestId);

}
