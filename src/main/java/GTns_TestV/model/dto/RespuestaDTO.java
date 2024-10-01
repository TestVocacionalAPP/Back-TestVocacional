package GTns_TestV.model.dto;

import lombok.Data;

@Data
public class RespuestaDTO {
    private Long idPregunta;
    private Integer valor; // Valor de la respuesta (1 para Sí, 0 para No)
}
