package GTns_TestV.service;

import GTns_TestV.model.dto.asesoria.AsesoriaCreateDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaResponseDTO;

import java.util.List;

public interface AsesoriaService {
    AsesoriaResponseDTO solicitarAsesoria(Long usuarioId, AsesoriaCreateDTO asesoriaCreateDTO);
    List<AsesoriaResponseDTO> listarSolicitudesPorExperto(Long expertoId);
}
