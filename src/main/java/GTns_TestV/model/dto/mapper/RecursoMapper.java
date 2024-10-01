package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.recurso.RecursoCreateDTO;
import GTns_TestV.model.dto.recurso.RecursoResponseDTO;
import GTns_TestV.model.entity.Recurso;
import org.springframework.stereotype.Component;

@Component
public class RecursoMapper {

    public Recurso toEntity(RecursoCreateDTO recursoCreateDTO) {
        return new Recurso(
                null,  // El ID será generado automáticamente
                recursoCreateDTO.getTitulo(),
                recursoCreateDTO.getDescripcion(),
                recursoCreateDTO.getTipoRecurso(),
                recursoCreateDTO.getUrlRecurso()
        );
    }

    public RecursoResponseDTO toResponseDTO(Recurso recurso) {
        return new RecursoResponseDTO(
                recurso.getId(),
                recurso.getTitulo(),
                recurso.getDescripcion(),
                recurso.getTipoRecurso()
        );
    }
}
