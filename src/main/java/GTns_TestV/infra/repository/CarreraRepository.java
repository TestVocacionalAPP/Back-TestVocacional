package GTns_TestV.infra.repository;

import GTns_TestV.model.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
}
