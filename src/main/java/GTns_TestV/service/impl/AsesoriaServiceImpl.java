package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.AsesoriaRepository;
import GTns_TestV.infra.repository.UsuarioRepository;
import GTns_TestV.model.dto.AsesoriaDTO;
import GTns_TestV.model.entity.Asesoria;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.enums.Role;
import GTns_TestV.service.AsesoriaService;
import GTns_TestV.model.dto.mapper.AsesoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AsesoriaServiceImpl implements AsesoriaService {

    private final AsesoriaRepository asesoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsesoriaMapper asesoriaMapper;

    @Override
    public AsesoriaDTO solicitarAsesoria(Long usuarioId, AsesoriaDTO asesoriaDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Asegurarse de que el experto no sea null
        if (asesoriaDTO.getExperto() == null || asesoriaDTO.getExperto().getId() == null) {
            throw new RuntimeException("El experto no ha sido proporcionado.");
        }

        Usuario experto = usuarioRepository.findById(asesoriaDTO.getExperto().getId())
                .orElseThrow(() -> new RuntimeException("Experto no encontrado"));

        // Validar que el usuario tenga el rol de "EXPERTO"
        if (!experto.getRole().equals(Role.EXPERTO)) {
            throw new RuntimeException("El usuario seleccionado no tiene el rol de EXPERTO.");
        }

        Asesoria asesoria = new Asesoria();
        asesoria.setUsuario(usuario);
        asesoria.setExperto(experto);
        asesoria.setAsunto(asesoriaDTO.getAsunto());
        asesoria.setFechaSolicitada(asesoriaDTO.getFechaSolicitada());

        Asesoria savedAsesoria = asesoriaRepository.save(asesoria);

        return asesoriaMapper.toDTO(savedAsesoria);
    }


}
