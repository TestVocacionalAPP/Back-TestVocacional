package GTns_TestV.model.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asesorias")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Asesoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;  // Usuario que solicita la asesoría

    @ManyToOne
    @JoinColumn(name = "experto_id", nullable = false)
    private Usuario experto;  // Experto que recibirá la solicitud

    @Column(nullable = false)
    private String asunto;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitada;
    @Column(nullable = true)
    private LocalDateTime fechaConfirmada;
}
