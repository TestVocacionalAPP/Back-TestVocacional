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

    private Integer interes;  // Campo para almacenar el resultado de Interés
    private Integer aptitud;  // Campo para almacenar el resultado de Aptitud
}
