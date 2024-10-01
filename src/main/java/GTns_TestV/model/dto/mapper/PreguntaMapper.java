package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.PreguntaDTO;
import GTns_TestV.model.entity.Pregunta;
import GTns_TestV.model.enums.TipoPregunta;
import org.springframework.stereotype.Component;

@Component
public class PreguntaMapper {

    // Convertir entidad Pregunta a PreguntaDTO
    public PreguntaDTO toDTO(Pregunta pregunta) {
        return new PreguntaDTO(
                pregunta.getIdPregunta(),
                pregunta.getEnunciado(),
                pregunta.getRespuestaSiNo(),
                pregunta.getPuntajePregunta(),
                pregunta.getTipoPregunta().name() // Asegúrate que 'tipoPregunta' es un enum o ajusta según el caso
        );
    }

    // Convertir PreguntaDTO a entidad Pregunta
    public Pregunta toEntity(PreguntaDTO preguntaDTO) {
        Pregunta pregunta = new Pregunta();
        pregunta.setIdPregunta(preguntaDTO.getIdPregunta());
        pregunta.setEnunciado(preguntaDTO.getEnunciado());
        pregunta.setRespuestaSiNo(preguntaDTO.getRespuestaSiNo());
        pregunta.setPuntajePregunta(preguntaDTO.getPuntajePregunta());
        // Asume que el tipo de pregunta es un enum y ajusta la conversión aquí si es necesario
        pregunta.setTipoPregunta(TipoPregunta.valueOf(preguntaDTO.getTipoPregunta()));
        return pregunta;
    }
}
