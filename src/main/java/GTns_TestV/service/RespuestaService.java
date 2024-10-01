package GTns_TestV.service;

import GTns_TestV.model.entity.Respuesta;

import java.util.List;
import java.util.Map;

public interface RespuestaService {
    Map<String, Object> calcularFilaConMayorRespuestas(Long idUsuario);

}
