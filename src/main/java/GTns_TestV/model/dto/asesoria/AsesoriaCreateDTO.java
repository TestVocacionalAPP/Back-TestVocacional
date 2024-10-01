package GTns_TestV.model.dto.asesoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsesoriaCreateDTO {
    private String asunto;
    private LocalDateTime fechaSolicitada;
    private Long usuarioId;
    private Long expertoId;
}
