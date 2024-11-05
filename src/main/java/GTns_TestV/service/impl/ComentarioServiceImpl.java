package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.ComentarioRepository;
import GTns_TestV.infra.repository.ExpertoRepository;
import GTns_TestV.model.dto.comentario.ComentarioResponseDTO;
import GTns_TestV.model.entity.Comentario;
import GTns_TestV.model.entity.Experto;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.ComentarioService;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final ExpertoRepository expertoRepository;
    private final UsuarioService usuarioService;

    @Override
    @Transactional
    public Comentario agregarComentario(Long expertoId, String contenido) {
        Usuario usuario = usuarioService.getAuthenticatedUser();
        Experto experto = expertoRepository.findById(expertoId)
                .orElseThrow(() -> new RuntimeException("Experto no encontrado"));

        Comentario comentario = Comentario.builder()
                .usuario(usuario)
                .experto(experto)
                .contenido(contenido)
                .fecha(LocalDateTime.now())
                .build();

        return comentarioRepository.save(comentario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioResponseDTO> listarComentariosPorExperto(Long expertoId) {
        Experto experto = expertoRepository.findById(expertoId)
                .orElseThrow(() -> new RuntimeException("Experto no encontrado"));

        List<Comentario> comentarios = comentarioRepository.findAllByExperto(experto);

        return comentarios.stream()
                .map(c -> new ComentarioResponseDTO(
                        c.getId(),
                        c.getContenido(),
                        c.getFecha(),
                        c.getUsuario().getNombre(),
                        c.getUsuario().getApellido(),
                        c.getUsuario().getId() // Agrega el usuarioId
                ))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Comentario editarComentario(Long comentarioId, Long usuarioId, String nuevoContenido) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        if (!comentario.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No tienes permiso para editar este comentario");
        }

        comentario.setContenido(nuevoContenido);
        comentario.setFecha(LocalDateTime.now());
        return comentarioRepository.save(comentario);
    }

    @Override
    @Transactional
    public void eliminarComentario(Long comentarioId, Long usuarioId) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        if (!comentario.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No tienes permiso para eliminar este comentario");
        }

        comentarioRepository.deleteById(comentarioId);
    }
}
