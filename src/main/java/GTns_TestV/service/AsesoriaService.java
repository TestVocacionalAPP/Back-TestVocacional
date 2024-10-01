package GTns_TestV.service;

import GTns_TestV.model.dto.asesoria.AsesoriaCreateDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaResponseDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaUpdateDTO;

import java.util.List;

public interface AsesoriaService {
    AsesoriaResponseDTO solicitarAsesoria(Long usuarioId, AsesoriaCreateDTO asesoriaCreateDTO);
    List<AsesoriaResponseDTO> listarSolicitudesPorExperto(Long expertoId);
    AsesoriaResponseDTO actualizarEstadoAsesoria(Long expertoId, AsesoriaUpdateDTO asesoriaUpdateDTO);
}
