package GTns_TestV.model.dto.asesoria;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsesoriaResponseDTO {
    private Long id;
    private String asunto;
    private LocalDateTime fechaSolicitada;
    private LocalDateTime fechaConfirmada;
    private String estado;
}
