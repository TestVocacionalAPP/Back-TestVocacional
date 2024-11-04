package GTns_TestV.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "historial_test")
public class HistorialTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_test", nullable = false)
    private Test test;

    private LocalDateTime fecha;

    // Campos adicionales para los resultados
    private String categoriaMayorInteres; // Campo para la categoría de mayor interés
    private String categoriaMayorAptitud; // Campo para la categoría de mayor aptitud
    private String mensajeIntereses; // Mensaje sobre los intereses
    private String mensajeCarreras; // Mensaje sobre las carreras
}
