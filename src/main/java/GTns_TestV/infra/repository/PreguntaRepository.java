package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.Pregunta;
import GTns_TestV.model.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    List<Pregunta> findByTest(Test test);
}
