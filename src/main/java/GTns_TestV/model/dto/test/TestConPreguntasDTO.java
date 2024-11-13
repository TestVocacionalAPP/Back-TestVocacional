package GTns_TestV.model.dto.test;

import GTns_TestV.model.dto.pregunta.PreguntaDTO;
import GTns_TestV.model.entity.Pregunta;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class TestConPreguntasDTO {
    private Long id;
    private String titulo;
    private List<PreguntaDTO> preguntas;
}