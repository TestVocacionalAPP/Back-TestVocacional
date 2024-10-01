package GTns_TestV.service;

import GTns_TestV.model.entity.Pregunta;
import java.util.List;

public interface PreguntaService {

    Pregunta crearPregunta(Pregunta pregunta);

    Pregunta obtenerPreguntaPorId(Long id);

    List<Pregunta> obtenerTodasLasPreguntas();

    Pregunta actualizarPregunta(Long id, Pregunta preguntaActualizada);

    void eliminarPregunta(Long id);
}
