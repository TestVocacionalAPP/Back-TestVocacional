package GTns_TestV.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaDTO {

    private Long idPregunta;
    private String enunciado;
    private Boolean respuestaSiNo;
    private Integer puntajePregunta;
    private String tipoPregunta;
}
