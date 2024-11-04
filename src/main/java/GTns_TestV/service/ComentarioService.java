package GTns_TestV.service;

import GTns_TestV.model.entity.Comentario;

import java.util.List;

public interface ComentarioService {
    Comentario agregarComentario(Long expertoId, String contenido);

    List<Comentario> listarComentariosPorExperto(Long expertoId);
}