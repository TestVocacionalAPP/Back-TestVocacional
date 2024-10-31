package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.Experto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertoRepository extends JpaRepository<Experto, Long> {
    List<Experto> findByEspecialidad(String especialidad);
}
