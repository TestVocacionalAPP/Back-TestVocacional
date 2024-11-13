package GTns_TestV.service;

import GTns_TestV.model.dto.experto.ExpertoCreateDTO;
import GTns_TestV.model.dto.experto.ExpertoPerfilDTO;
import GTns_TestV.model.dto.experto.ExpertoUpdateDTO;
import GTns_TestV.model.dto.experto.ExpertoResponseDTO;

import java.util.List;

public interface ExpertoService {
    ExpertoResponseDTO crearExperto(ExpertoCreateDTO expertoCreateDTO);
    ExpertoResponseDTO actualizarExperto(Long id, ExpertoUpdateDTO expertoUpdateDTO);
    List<ExpertoResponseDTO> obtenerTodosLosExpertos();
    ExpertoResponseDTO obtenerExpertoPorId(Long id);
    void eliminarExperto(Long id);
    List<ExpertoResponseDTO> buscarExpertosPorEspecialidad(String especialidad);
    ExpertoResponseDTO toggleLike(Long expertoId, Long usuarioId);


    ExpertoPerfilDTO obtenerPerfilExperto(Long id);
    ExpertoPerfilDTO actualizarPerfilExperto(ExpertoPerfilDTO expertoPerfilDTO);
    void actualizarImagenPerfil(String imagenBase64);

}
