package GTns_TestV.service;

import GTns_TestV.model.dto.RespuestaDTO;
import GTns_TestV.model.entity.HistorialTest;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;

import java.util.List;
import java.util.Map;

public interface RespuestaService {
    Map<String, Object> calcularFilaConMayorRespuestas(Long historialTestId);
    HistorialTest procesarRespuestas(Usuario usuario, Test test, List<RespuestaDTO> respuestasDTO);
}
