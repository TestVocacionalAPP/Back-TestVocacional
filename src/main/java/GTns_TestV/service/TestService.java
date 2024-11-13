package GTns_TestV.service;

import GTns_TestV.model.dto.test.TestCreationDTO;
import GTns_TestV.model.dto.test.TestResponseDTO;
import GTns_TestV.model.dto.test.TestUpdateDTO;
import GTns_TestV.model.entity.Pregunta;
import GTns_TestV.model.entity.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TestService {

    TestResponseDTO crearTest(TestCreationDTO testCreationDTO, Long idUsuario);

    TestResponseDTO crearTestConPreguntas(String titulo, Long idUsuario, MultipartFile file);

    List<TestResponseDTO> obtenerTodosLosTests();

    Test obtenerTestPorId(Long id);

    void cargarPreguntasDesdeCSV(MultipartFile file, Long idTest, Long idUsuario);

    List<TestResponseDTO> obtenerTodosLosTestsPorUsuario(Long idUsuario);

    TestResponseDTO obtenerTestPorIdYUsuario(Long idTest, Long idUsuario);

    TestResponseDTO actualizarTest(Long idTest, TestUpdateDTO testUpdateDTO);

    List<Pregunta> obtenerPreguntasPorTest(Long idTest);

}
