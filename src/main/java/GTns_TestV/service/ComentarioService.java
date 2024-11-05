package GTns_TestV.service;

import GTns_TestV.model.dto.comentario.ComentarioResponseDTO;
import GTns_TestV.model.entity.Comentario;

import java.util.List;

public interface ComentarioService {
    Comentario agregarComentario(Long expertoId, String contenido);

    List<ComentarioResponseDTO> listarComentariosPorExperto(Long expertoId);
    Comentario editarComentario(Long comentarioId, Long usuarioId, String nuevoContenido);

    void eliminarComentario(Long comentarioId, Long usuarioId);
}
