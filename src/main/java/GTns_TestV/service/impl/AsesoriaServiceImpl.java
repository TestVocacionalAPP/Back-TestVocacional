package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.AsesoriaRepository;
import GTns_TestV.infra.repository.UsuarioRepository;
import GTns_TestV.model.dto.asesoria.AsesoriaCreateDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaResponseDTO;
import GTns_TestV.model.entity.Asesoria;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.entity.Experto;
import GTns_TestV.service.AsesoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsesoriaServiceImpl implements AsesoriaService {

    private final AsesoriaRepository asesoriaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public AsesoriaResponseDTO solicitarAsesoria(AsesoriaCreateDTO asesoriaCreateDTO, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Experto experto = (Experto) usuarioRepository.findById(asesoriaCreateDTO.getExpertoId())
                .orElseThrow(() -> new RuntimeException("Experto no encontrado"));

        Asesoria asesoria = Asesoria.builder()
                .usuario(usuario)
                .experto(experto)
                .asunto(asesoriaCreateDTO.getAsunto())
                .fechaSolicitada(asesoriaCreateDTO.getFechaSolicitada())
                .descripcion(asesoriaCreateDTO.getDescripcion())  // Nuevo campo
                .metodoContacto(asesoriaCreateDTO.getMetodoContacto())  // Nuevo campo
                .duracion(asesoriaCreateDTO.getDuracion())  // Nuevo campo
                .estado("PENDIENTE")
                .build();


        Asesoria savedAsesoria = asesoriaRepository.save(asesoria);

        return mapToResponseDTO(savedAsesoria);
    }

    @Override
    public AsesoriaResponseDTO confirmarAsesoria(Long asesoriaId, Long expertoId) {
        Asesoria asesoria = asesoriaRepository.findById(asesoriaId)
                .orElseThrow(() -> new RuntimeException("Asesoría no encontrada"));

        if (!asesoria.getExperto().getId().equals(expertoId)) {
            throw new SecurityException("Acceso denegado. Solo el experto asignado puede confirmar esta asesoría.");
        }

        asesoria.setFechaConfirmada(LocalDateTime.now());
        asesoria.setEstado("CONFIRMADA");

        Asesoria confirmedAsesoria = asesoriaRepository.save(asesoria);
        return mapToResponseDTO(confirmedAsesoria);
    }

    @Override
    public List<AsesoriaResponseDTO> obtenerAsesoriasPorUsuario(Long usuarioId) {
        List<Asesoria> asesorias = asesoriaRepository.findByUsuarioId(usuarioId);
        return asesorias.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<AsesoriaResponseDTO> obtenerAsesoriasPorExperto(Long expertoId) {
        List<Asesoria> asesorias = asesoriaRepository.findByExpertoId(expertoId);
        return asesorias.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    private AsesoriaResponseDTO mapToResponseDTO(Asesoria asesoria) {
        return new AsesoriaResponseDTO(
                asesoria.getId(),
                asesoria.getAsunto(),
                asesoria.getFechaSolicitada(),
                asesoria.getFechaConfirmada(),
                asesoria.getEstado(),
                asesoria.getUsuario().getNombre(),
                asesoria.getUsuario().getCorreo()
        );
    }
    @Override
    public AsesoriaResponseDTO aceptarSolicitud(Long asesoriaId, Long expertoId) {
        Asesoria asesoria = asesoriaRepository.findById(asesoriaId)
                .orElseThrow(() -> new RuntimeException("Asesoría no encontrada"));

        if (!asesoria.getExperto().getId().equals(expertoId)) {
            throw new SecurityException("Acceso denegado. Solo el experto asignado puede aceptar esta asesoría.");
        }

        asesoria.setEstado("ACEPTADA");
        asesoria.setFechaConfirmada(LocalDateTime.now());
        Asesoria asesoriaAceptada = asesoriaRepository.save(asesoria);
        return mapToResponseDTO(asesoriaAceptada);
    }

    @Override
    public AsesoriaResponseDTO rechazarSolicitud(Long asesoriaId, Long expertoId) {
        Asesoria asesoria = asesoriaRepository.findById(asesoriaId)
                .orElseThrow(() -> new RuntimeException("Asesoría no encontrada"));

        if (!asesoria.getExperto().getId().equals(expertoId)) {
            throw new SecurityException("Acceso denegado. Solo el experto asignado puede rechazar esta asesoría.");
        }

        asesoria.setEstado("RECHAZADA");
        Asesoria asesoriaRechazada = asesoriaRepository.save(asesoria);
        return mapToResponseDTO(asesoriaRechazada);
    }
    @Override
    public List<String> verificarYNotificarCitas(Long usuarioId) {
        List<Asesoria> asesorias = asesoriaRepository.findByUsuarioId(usuarioId);
        List<String> notificaciones = new ArrayList<>();

        for (Asesoria asesoria : asesorias) {
            if ("CONFIRMADA".equals(asesoria.getEstado())) {
                String mensaje = "Notificación: Tu solicitud de asesoría con el experto "
                        + asesoria.getExperto().getNombre() + " ha sido aceptada.";
                notificaciones.add(mensaje);
            } else if ("RECHAZADA".equals(asesoria.getEstado())) {
                String mensaje = "Notificación: Tu solicitud de asesoría con el experto "
                        + asesoria.getExperto().getNombre() + " ha sido rechazada.";
                notificaciones.add(mensaje);
            }
        }
        return notificaciones;
    }


}
