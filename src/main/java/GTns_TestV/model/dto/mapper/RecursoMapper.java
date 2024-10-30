package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.recurso.RecursoCreateDTO;
import GTns_TestV.model.dto.recurso.RecursoResponseDTO;
import GTns_TestV.model.entity.Recurso;
import org.springframework.stereotype.Component;

@Component
public class RecursoMapper {

    public Recurso toEntity(RecursoCreateDTO recursoCreateDTO) {
        return Recurso.builder()
                .titulo(recursoCreateDTO.getTitulo())
                .descripcion(recursoCreateDTO.getDescripcion())
                .tipoRecurso(recursoCreateDTO.getTipoRecurso())
                .urlRecurso(recursoCreateDTO.getUrlRecurso())
                .build();
    }

    public RecursoResponseDTO toResponseDTO(Recurso recurso) {
        return RecursoResponseDTO.builder()
                .id(recurso.getId())
                .titulo(recurso.getTitulo())
                .descripcion(recurso.getDescripcion())
                .tipoRecurso(recurso.getTipoRecurso())
                .urlRecurso(recurso.getUrlRecurso())
                .build();
    }
    }

