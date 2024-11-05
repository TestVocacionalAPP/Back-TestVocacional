package GTns_TestV.controller;

import GTns_TestV.model.dto.comentario.ComentarioResponseDTO;
import GTns_TestV.model.entity.Comentario;
import GTns_TestV.service.ComentarioService;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {
    private final ComentarioService comentarioService;
    private final UsuarioService usuarioService;


    @PostMapping("/agregar/{expertoId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comentario> agregarComentario(@PathVariable Long expertoId, @RequestBody String contenido) {
        Comentario comentario = comentarioService.agregarComentario(expertoId, contenido);
        return ResponseEntity.ok(comentario);
    }

    @GetMapping("/experto/{expertoId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ComentarioResponseDTO>> listarComentariosPorExperto(@PathVariable Long expertoId) {
        List<ComentarioResponseDTO> comentarios = comentarioService.listarComentariosPorExperto(expertoId);
        return ResponseEntity.ok(comentarios);
    }

    @PutMapping("/editar/{comentarioId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ComentarioResponseDTO> editarComentario(
            @PathVariable Long comentarioId,
            @RequestBody Map<String, String> nuevoContenido) {

        Long usuarioId = usuarioService.getAuthenticatedUser().getId();
        Comentario comentarioEditado = comentarioService.editarComentario(comentarioId, usuarioId, nuevoContenido.get("contenido"));

        ComentarioResponseDTO responseDTO = new ComentarioResponseDTO(
                comentarioEditado.getId(),
                comentarioEditado.getContenido(),
                comentarioEditado.getFecha(),
                comentarioEditado.getUsuario().getNombre(),
                comentarioEditado.getUsuario().getApellido(),
                comentarioEditado.getUsuario().getId() // Incluye el usuarioId aquí
        );
        return ResponseEntity.ok(responseDTO);
    }


    @DeleteMapping("/eliminar/{comentarioId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long comentarioId) {
        Long usuarioId = usuarioService.getAuthenticatedUser().getId();
        comentarioService.eliminarComentario(comentarioId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
