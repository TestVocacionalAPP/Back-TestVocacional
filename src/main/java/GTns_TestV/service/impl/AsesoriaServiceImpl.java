package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.AsesoriaRepository;
import GTns_TestV.infra.repository.UsuarioRepository;
import GTns_TestV.model.dto.asesoria.AsesoriaCreateDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaResponseDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaUpdateDTO;
import GTns_TestV.model.entity.Asesoria;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.enums.Role;
import GTns_TestV.service.AsesoriaService;
import GTns_TestV.model.dto.mapper.AsesoriaMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsesoriaServiceImpl implements AsesoriaService {

    private final AsesoriaRepository asesoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsesoriaMapper asesoriaMapper;

    @Override
    public AsesoriaResponseDTO solicitarAsesoria(Long usuarioId, AsesoriaCreateDTO asesoriaCreateDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Asegurarse de que el experto no sea null
        if (asesoriaCreateDTO.getExpertoId() == null) {
            throw new RuntimeException("El experto no ha sido proporcionado.");
        }

        Usuario experto = usuarioRepository.findById(asesoriaCreateDTO.getExpertoId())
                .orElseThrow(() -> new RuntimeException("Experto no encontrado"));

        // Validar que el usuario tenga el rol de "EXPERTO"
        if (!experto.getRole().equals(Role.EXPERTO)) {
            throw new RuntimeException("El usuario seleccionado no tiene el rol de EXPERTO.");
        }

        Asesoria asesoria = new Asesoria();
        asesoria.setUsuario(usuario);
        asesoria.setExperto(experto);
        asesoria.setAsunto(asesoriaCreateDTO.getAsunto());
        asesoria.setFechaSolicitada(asesoriaCreateDTO.getFechaSolicitada());

        Asesoria savedAsesoria = asesoriaRepository.save(asesoria);

        return asesoriaMapper.toResponseDTO(savedAsesoria);
    }

    @Override
    public List<AsesoriaResponseDTO> listarSolicitudesPorExperto(Long expertoId) {
        // Obtener todas las asesorías donde el experto es el usuario autenticado
        List<Asesoria> solicitudes = asesoriaRepository.findByExpertoId(expertoId);

        // Mapear las entidades Asesoria a DTOs
        return solicitudes.stream()
                .map(asesoriaMapper::toResponseDTO)
                .toList();
    }
    @Override
    @Transactional
    public AsesoriaResponseDTO actualizarEstadoAsesoria(Long expertoId, AsesoriaUpdateDTO asesoriaUpdateDTO) {
        Asesoria asesoria = asesoriaRepository.findById(asesoriaUpdateDTO.getId())
                .orElseThrow(() -> new RuntimeException("Asesoría no encontrada"));

        // Verificar que el experto asociado a la asesoría sea el que está autenticado
        if (!asesoria.getExperto().getId().equals(expertoId)) {
            throw new RuntimeException("No autorizado para actualizar esta asesoría.");
        }

        // Actualizar el estado de la asesoría
        if ("aceptada".equalsIgnoreCase(asesoriaUpdateDTO.getEstado())) {
            asesoria.setFechaConfirmada(asesoriaUpdateDTO.getFechaConfirmada());
            asesoria.setEstado("aceptada");
        } else if ("rechazada".equalsIgnoreCase(asesoriaUpdateDTO.getEstado())) {
            asesoria.setEstado("rechazada");
        } else {
            throw new RuntimeException("Estado inválido. Debe ser 'aceptada' o 'rechazada'.");
        }

        Asesoria asesoriaActualizada = asesoriaRepository.save(asesoria);
        return asesoriaMapper.toResponseDTO(asesoriaActualizada);  // Usar toResponseDTO
    }

}
