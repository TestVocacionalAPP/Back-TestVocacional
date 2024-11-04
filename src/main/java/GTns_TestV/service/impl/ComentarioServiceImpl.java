package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.ComentarioRepository;
import GTns_TestV.infra.repository.ExpertoRepository;
import GTns_TestV.model.entity.Comentario;
import GTns_TestV.model.entity.Experto;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.ComentarioService;
import GTns_TestV.service.UsuarioService; // Agrega esta importación
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final ExpertoRepository expertoRepository;
    private final UsuarioService usuarioService; // Agrega esta línea para la inyección de dependencias

    @Override
    @Transactional
    public Comentario agregarComentario(Long expertoId, String contenido) {
        Usuario usuario = usuarioService.getAuthenticatedUser(); // Este método se usa para obtener al usuario autenticado
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
    public List<Comentario> listarComentariosPorExperto(Long expertoId) {
        Experto experto = expertoRepository.findById(expertoId)
                .orElseThrow(() -> new RuntimeException("Experto no encontrado"));

        return comentarioRepository.findAllByExperto(experto);
    }
}
