package GTns_TestV.model.entity;

import GTns_TestV.model.enums.EstadoCompra;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "compra_recurso")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompraRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "recurso_id", nullable = false)
    private Recurso recurso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCompra estado;

    @Column(length = 4)
    private String ultimosDigitosTarjeta;

    @Column(name = "tipo_tarjeta")
    private String tipoTarjeta; // Ejemplo: "Visa", "Mastercard"

    private Integer cantidad;  // Agrega el campo si no existe
    @Column(name = "fecha")
    private LocalDate fecha;  // Fecha de la compra
}
