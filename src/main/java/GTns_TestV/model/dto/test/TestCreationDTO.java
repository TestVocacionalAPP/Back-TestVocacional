package GTns_TestV.model.dto.test;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestCreationDTO {
    private String titulo;
    private Double puntaje;
}
