package GTns_TestV.model.dto.test;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResponseDTO {
    private Long id;
    private String titulo;
    private Double puntaje;
}
