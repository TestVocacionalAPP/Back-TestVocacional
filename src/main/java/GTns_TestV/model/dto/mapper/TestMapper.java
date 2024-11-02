package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.pregunta.PreguntaDTO;
import GTns_TestV.model.dto.test.TestCreationDTO;
import GTns_TestV.model.dto.test.TestUpdateDTO;
import GTns_TestV.model.dto.test.TestResponseDTO;
import GTns_TestV.model.dto.test.TestConPreguntasDTO;
import GTns_TestV.model.entity.Pregunta;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestMapper {

    // Convierte de TestCreationDTO a Test
    public Test toEntity(TestCreationDTO dto, Usuario usuario) {
        return Test.builder()
                .titulo(dto.getTitulo())
                .usuario(usuario)
                .build();
    }

    // Convierte de Test a TestResponseDTO
    public TestResponseDTO toResponseDTO(Test test) {
        return TestResponseDTO.builder()
                .id(test.getId())
                .titulo(test.getTitulo())
                .build();
    }

    // Convierte de Test a TestUpdateDTO
    public TestUpdateDTO toUpdateDTO(Test test) {
        return TestUpdateDTO.builder()
                .titulo(test.getTitulo())
                .build();
    }

    // Convierte de Test a TestConPreguntasDTO
    public TestConPreguntasDTO toTestConPreguntasDTO(Test test) {
        List<PreguntaDTO> preguntasDTO = test.getPreguntas().stream()
                .map(this::toPreguntaDTO)
                .collect(Collectors.toList());

        return TestConPreguntasDTO.builder()
                .id(test.getId())
                .titulo(test.getTitulo())
                .preguntas(preguntasDTO)
                .build();
    }

    // Actualiza un Test existente con datos de TestUpdateDTO
    public Test updateEntity(TestUpdateDTO dto, Test testExistente) {
        testExistente.setTitulo(dto.getTitulo() != null ? dto.getTitulo() : testExistente.getTitulo());
        return testExistente;
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
