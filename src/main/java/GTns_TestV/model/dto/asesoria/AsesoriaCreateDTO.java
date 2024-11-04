package GTns_TestV.model.dto.asesoria;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsesoriaCreateDTO {
    private Long expertoId;
    private String asunto;
    private LocalDateTime fechaSolicitada;
    private String descripcion;
    private String metodoContacto;
    private Integer duracion;

}
