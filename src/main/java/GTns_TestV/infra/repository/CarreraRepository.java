package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.Carrera;
import GTns_TestV.model.enums.ChasideCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    @Query("SELECT c FROM Carrera c WHERE c.categoria = :categoria")
    List<Carrera> findByCategoria(@Param("categoria") ChasideCategory categoria);

    List<Carrera> findByNombreIn(List<String> nombres);

}
