package GTns_TestV.model.dto.test;

import GTns_TestV.model.dto.PreguntaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestConPreguntasDTO {
    private Long idTest;
    private String titulo;
    private Double puntaje;
    private List<PreguntaDTO> preguntas;
}
