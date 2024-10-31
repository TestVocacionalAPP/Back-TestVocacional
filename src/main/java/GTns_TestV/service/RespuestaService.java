package GTns_TestV.service;

import GTns_TestV.model.dto.respuesta.RespuestaDTO;
import GTns_TestV.model.entity.HistorialTest;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;

import java.util.List;
import java.util.Map;

public interface RespuestaService {
    //Map<String, Object> calcularFilaConMayorRespuestas(Long historialTestId);
    Map<String, Object> calcularFilaConMayorRespuestas(Long historialTestId, Long idTest);
    HistorialTest procesarRespuestas(Usuario usuario, Test test, List<RespuestaDTO> respuestasDTO);
}
