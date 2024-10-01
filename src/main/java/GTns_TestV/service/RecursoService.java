package GTns_TestV.service;

import GTns_TestV.model.dto.recurso.RecursoCreateDTO;
import GTns_TestV.model.dto.recurso.RecursoResponseDTO;

import java.util.List;

public interface RecursoService {
    RecursoResponseDTO crearRecurso(RecursoCreateDTO recursoCreateDTO);
    List<RecursoResponseDTO> listarRecursos();

    List<RecursoResponseDTO> buscarRecursos(String titulo, String descripcion);
}
