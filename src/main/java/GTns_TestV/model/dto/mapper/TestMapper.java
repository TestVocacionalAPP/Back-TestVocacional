package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.PreguntaDTO;
import GTns_TestV.model.dto.test.TestConPreguntasDTO;
import GTns_TestV.model.dto.test.TestCreationDTO;
import GTns_TestV.model.dto.test.TestUpdateDTO;
import GTns_TestV.model.dto.test.TestResponseDTO;
import GTns_TestV.model.entity.Pregunta;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class TestMapper {

    // Convierte de TestCreationDTO a Test
    public Test toEntity(TestCreationDTO dto, Usuario usuario) {
        return Test.builder()
                .titulo(dto.getTitulo())
                .puntaje(dto.getPuntaje())
                .usuario(usuario)
                .build();
    }

    // Convierte de Test a TestResponseDTO
    public TestResponseDTO toResponseDTO(Test test) {
        return TestResponseDTO.builder()
                .id(test.getId())
                .titulo(test.getTitulo())
                .puntaje(test.getPuntaje())
                .build();
    }

    // Convierte de Test a TestUpdateDTO
    public TestUpdateDTO toUpdateDTO(Test test) {
        return TestUpdateDTO.builder()
                .titulo(test.getTitulo())
                .puntaje(test.getPuntaje())
                .build();
    }

    // Actualiza un Test existente con datos de TestUpdateDTO
    public Test updateEntity(TestUpdateDTO dto, Test testExistente) {
        testExistente.setTitulo(dto.getTitulo() != null ? dto.getTitulo() : testExistente.getTitulo());
        testExistente.setPuntaje(dto.getPuntaje() != null ? dto.getPuntaje() : testExistente.getPuntaje());
        return testExistente;
    }
    public TestConPreguntasDTO toTestConPreguntasDTO(Test test) {
        List<PreguntaDTO> preguntas = test.getPreguntas().stream()
                .map(this::toPreguntaDTO)
                .collect(Collectors.toList());

        return TestConPreguntasDTO.builder()
                .idTest(test.getId())
                .titulo(test.getTitulo())
                .puntaje(test.getPuntaje())
                .preguntas(preguntas)
                .build();
    }
    private PreguntaDTO toPreguntaDTO(Pregunta pregunta) {
        return new PreguntaDTO(
                pregunta.getIdPregunta(),
                pregunta.getEnunciado(),
                pregunta.getRespuestaSiNo(),
                pregunta.getPuntajePregunta(),
                pregunta.getTipoPregunta().name()  // Convierte el enum a String
        );
    }
}
