package GTns_TestV.service;

import GTns_TestV.model.dto.asesoria.AsesoriaCreateDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaResponseDTO;

import java.util.List;

public interface AsesoriaService {
    AsesoriaResponseDTO solicitarAsesoria(AsesoriaCreateDTO asesoriaCreateDTO, Long usuarioId);
    AsesoriaResponseDTO confirmarAsesoria(Long asesoriaId, Long expertoId);
    List<AsesoriaResponseDTO> obtenerAsesoriasPorUsuario(Long usuarioId);
    List<AsesoriaResponseDTO> obtenerAsesoriasPorExperto(Long expertoId);

    AsesoriaResponseDTO aceptarSolicitud(Long asesoriaId, Long expertoId);
    AsesoriaResponseDTO rechazarSolicitud(Long asesoriaId, Long expertoId);

    List<String> verificarYNotificarCitas(Long usuarioId);
    List<AsesoriaResponseDTO> obtenerNotificaciones(Long expertoId);
}
