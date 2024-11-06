package GTns_TestV.service;

import GTns_TestV.model.dto.CompraResponseDTO;
import GTns_TestV.model.dto.PagoDTO;
import GTns_TestV.model.dto.recurso.RecursoCreateDTO;
import GTns_TestV.model.dto.recurso.RecursoResponseDTO;

import java.util.List;

public interface RecursoService {
    RecursoResponseDTO crearRecurso(RecursoCreateDTO recursoCreateDTO);

    List<RecursoResponseDTO> listarRecursos();

    List<RecursoResponseDTO> buscarRecursosPorTitulo(String titulo);

    RecursoResponseDTO actualizarRecurso(Long id, RecursoCreateDTO recursoCreateDTO);

    void eliminarRecurso(Long id);

    CompraResponseDTO comprarRecurso(Long idRecurso, PagoDTO pagoDTO, Integer cantidad);
    List<CompraResponseDTO> obtenerHistorialCompras();
}