package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.PreguntaRepository;
import GTns_TestV.infra.repository.TestRepository;
import GTns_TestV.model.entity.Pregunta;
import GTns_TestV.model.entity.Test;
import GTns_TestV.service.PreguntaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreguntaServiceImpl implements PreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final TestRepository testRepository;
    @Override
    @Transactional
    public Pregunta crearPregunta(Pregunta pregunta) {
        return preguntaRepository.save(pregunta);
    }

    @Override
    public Pregunta obtenerPreguntaPorId(Long id) {
        return preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
    }

    @Override
    public List<Pregunta> obtenerTodasLasPreguntas() {
        return preguntaRepository.findAll();
    }

    @Override
    @Transactional
    public Pregunta actualizarPregunta(Long id, Pregunta preguntaActualizada) {
        Pregunta preguntaExistente = obtenerPreguntaPorId(id);

        preguntaExistente.setEnunciado(preguntaActualizada.getEnunciado());
        preguntaExistente.setPuntajePregunta(preguntaActualizada.getPuntajePregunta());
        preguntaExistente.setRespuestaSiNo(preguntaActualizada.getRespuestaSiNo());

        return preguntaRepository.save(preguntaExistente);
    }

    @Override
    @Transactional
    public void eliminarPregunta(Long id) {
        Pregunta pregunta = obtenerPreguntaPorId(id);
        preguntaRepository.delete(pregunta);
    }

    // Método para obtener todas las preguntas de un test específico
    public List<Pregunta> obtenerPreguntasPorTest(Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test no encontrado"));

        return test.getPreguntas();  // Devuelve la lista de preguntas asociadas al test
    }
}
