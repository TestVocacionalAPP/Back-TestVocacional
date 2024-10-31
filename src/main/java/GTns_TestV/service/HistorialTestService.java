package GTns_TestV.service;

import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.entity.Test;
import java.util.Map;

public interface HistorialTestService {
    void guardarResultadosEnHistorial(Usuario usuario, Test test, Map<String, Integer> resultados);
    Map<String, Integer> obtenerResultadosHistoricos(Long idUsuario, Long idTest);
    Map<String, Object> obtenerResultadosDelTest(Long historialTestId); // Declaración pública
}
