package GTns_TestV.model.dto.asesoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsesoriaUpdateDTO {
    private Long id;
    private LocalDateTime fechaConfirmada;
    private String estado;
}
