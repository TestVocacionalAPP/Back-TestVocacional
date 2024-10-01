package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.test.TestCreationDTO;
import GTns_TestV.model.dto.test.TestUpdateDTO;
import GTns_TestV.model.dto.test.TestResponseDTO;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;
import org.springframework.stereotype.Component;

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
}
