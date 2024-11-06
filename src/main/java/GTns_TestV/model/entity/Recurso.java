package GTns_TestV.model.entity;

import GTns_TestV.model.enums.CategoriaRecurso;
import GTns_TestV.model.enums.TipoRecurso;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "recursos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING) // Esto guarda el nombre del enum como texto
    @Column(nullable = false)
    private TipoRecurso tipoRecurso;

    @Column(nullable = false)
    private String urlRecurso;  // URL de acceso al recurso

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaRecurso categoriaRecurso;

    @Column(nullable = false)
    private boolean esPremium;

    private boolean tieneAcceso;

    @Column(nullable = false)
    private BigDecimal precio;

}
