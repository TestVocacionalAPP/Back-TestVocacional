package GTns_TestV.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import GTns_TestV.model.enums.TipoPregunta;  // Enum para diferenciar si es interés o aptitud
import GTns_TestV.model.enums.Interes;      // Enum de intereses
import GTns_TestV.model.enums.Aptitud;      // Enum de aptitudes

@Entity
@Data
@Table(name = "respuestas")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Enum para diferenciar si es una respuesta de interés o aptitud
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPregunta tipoPregunta;

    // Si la respuesta es de interés, se usará este campo
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Interes interes;  // Enum para las categorías de interés (C, H, A, S, I, D, E)

    // Si la respuesta es de aptitud, se usará este campo
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Aptitud aptitud;  // Enum para las categorías de aptitud (C, H, A, S, I, D, E)

    @Column(nullable = false)
    private Integer valor; // El valor asociado a esa categoría (1 o 0)

    // Relación ManyToOne con Usuario (quien respondió)
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Relación ManyToOne con Pregunta
    @ManyToOne
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta pregunta;

}
