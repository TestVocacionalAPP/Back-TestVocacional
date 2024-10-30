package GTns_TestV.model.entity;

import jakarta.persistence.*;
import GTns_TestV.model.enums.ChasideCategory; // Asegúrate de importar tu enum
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carreras")
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING) // Para almacenar el enum como cadena
    @Column(nullable = false)
    private ChasideCategory categoria; // Campo que representa la categoría de CHASIDE
}
