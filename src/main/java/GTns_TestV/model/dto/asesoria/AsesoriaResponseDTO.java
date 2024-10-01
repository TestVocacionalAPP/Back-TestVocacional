package GTns_TestV.model.dto.asesoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsesoriaResponseDTO {
    private Long id;
    private String asunto;
    private LocalDateTime fechaSolicitada;
    private LocalDateTime fechaConfirmada;
    private String usuarioNombre;
    private String expertoNombre;
}
