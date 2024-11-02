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
                asesoria.getEstado()
        );
    }
}
