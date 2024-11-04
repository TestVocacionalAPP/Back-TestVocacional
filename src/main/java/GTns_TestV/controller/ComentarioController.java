package GTns_TestV.controller;

import GTns_TestV.model.entity.Comentario;
import GTns_TestV.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {
    private final ComentarioService comentarioService;

    @PostMapping("/agregar/{expertoId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comentario> agregarComentario(@PathVariable Long expertoId, @RequestBody String contenido) {
        Comentario comentario = comentarioService.agregarComentario(expertoId, contenido);
        return ResponseEntity.ok(comentario);
    }


}
