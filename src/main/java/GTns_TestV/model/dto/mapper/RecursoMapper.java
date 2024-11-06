package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.recurso.RecursoCreateDTO;
import GTns_TestV.model.dto.recurso.RecursoResponseDTO;
import GTns_TestV.model.entity.Recurso;
import GTns_TestV.model.enums.CategoriaRecurso;
import org.springframework.stereotype.Component;

@Component
public class RecursoMapper {

    public Recurso toEntity(RecursoCreateDTO recursoCreateDTO) {
        return Recurso.builder()
                .titulo(recursoCreateDTO.getTitulo())
                .descripcion(recursoCreateDTO.getDescripcion())
                .tipoRecurso(recursoCreateDTO.getTipoRecurso())
                .categoriaRecurso(recursoCreateDTO.getCategoriaRecurso())
                .urlRecurso(recursoCreateDTO.getUrlRecurso())
                .precio(recursoCreateDTO.getPrecio()) // No necesita conversión
                .esPremium(recursoCreateDTO.getCategoriaRecurso() == CategoriaRecurso.PREMIUM)
                .build();
    }

    public RecursoResponseDTO toResponseDTO(Recurso recurso) {
        return RecursoResponseDTO.builder()
                .id(recurso.getId())
                .titulo(recurso.getTitulo())
                .descripcion(recurso.getDescripcion())
                .tipoRecurso(recurso.getTipoRecurso())
                .categoriaRecurso(recurso.getCategoriaRecurso())
                .urlRecurso(recurso.getUrlRecurso())
                .precio(recurso.getPrecio()) // No necesita conversión
                .tieneAcceso(false)
                .build();
    }

    public RecursoResponseDTO toResponseDTO(Recurso recurso, boolean tieneAcceso) {
        return RecursoResponseDTO.builder()
                .id(recurso.getId())
                .titulo(recurso.getTitulo())
                .descripcion(recurso.getDescripcion())
                .tipoRecurso(recurso.getTipoRecurso())
                .categoriaRecurso(recurso.getCategoriaRecurso())
                .urlRecurso(recurso.getUrlRecurso())
                .precio(recurso.getPrecio()) // No necesita conversión
                .tieneAcceso(tieneAcceso)
                .build();
    }
}
