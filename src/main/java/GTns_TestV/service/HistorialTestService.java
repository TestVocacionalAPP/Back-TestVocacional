package GTns_TestV.service;

import GTns_TestV.model.entity.HistorialTest;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;

import java.util.Map;

public interface HistorialTestService {
    void guardarResultadosEnHistorial(Usuario usuario, Test test, Map<String, Integer> resultados);
    Map<String, Integer> obtenerResultadosHistoricos(Long idUsuario, Long idTest);
}
