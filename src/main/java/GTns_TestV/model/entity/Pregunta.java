package GTns_TestV.model.entity;

import GTns_TestV.model.enums.TipoPregunta; // Enum nuevo para diferenciar Intereses y Aptitudes
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "preguntas")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPregunta;

    @Column(nullable = false, length = 500)
    private String enunciado; // El enunciado o texto de la pregunta

    @Column(nullable = true) // Permite valores nulos
    private Boolean respuestaSiNo;

    @Column(nullable = false)
    private Integer puntajePregunta = 0; // Valor por defecto 0

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPregunta tipoPregunta; // Para diferenciar entre Intereses y Aptitudes

    // Relación ManyToOne con la clase Test
    @ManyToOne
    @JoinColumn(name = "idTest", nullable = false)
    private Test test;
    // Nuevo campo para la categoría
    private String categoria;
}
