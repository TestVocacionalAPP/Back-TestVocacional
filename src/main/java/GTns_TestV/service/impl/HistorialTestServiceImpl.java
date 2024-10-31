package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.HistorialTestRepository;
import GTns_TestV.model.entity.HistorialTest;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.entity.Test;
import GTns_TestV.service.HistorialTestService;
import GTns_TestV.service.RespuestaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistorialTestServiceImpl implements HistorialTestService {

    private final HistorialTestRepository historialTestRepository;
    private final RespuestaService respuestaService; // Asegúrate de inyectar el servicio de respuesta

    @Override
    public void guardarResultadosEnHistorial(Usuario usuario, Test test, Map<String, Integer> resultados) {
        HistorialTest historialTest = new HistorialTest();
        historialTest.setUsuario(usuario);
        historialTest.setTest(test);
        historialTest.setFecha(LocalDateTime.now());

        // Asignar solo los resultados de interés y aptitud
        historialTest.setInteres(resultados.getOrDefault("Interes", 0));
        historialTest.setAptitud(resultados.getOrDefault("Aptitud", 0));

        historialTestRepository.save(historialTest);
    }

    @Override
    public Map<String, Integer> obtenerResultadosHistoricos(Long idUsuario, Long idTest) {
        Optional<HistorialTest> optionalHistorialTest = historialTestRepository.findByUsuarioIdAndTestId(idUsuario, idTest);

        HistorialTest historialTest = optionalHistorialTest
                .orElseThrow(() -> new RuntimeException("No se encontró historial para el usuario y test especificados."));

        Map<String, Integer> resultados = new HashMap<>();
        resultados.put("Interes", historialTest.getInteres());
        resultados.put("Aptitud", historialTest.getAptitud());

        return resultados;
    }

    @Override
    public Map<String, Object> obtenerResultadosDelTest(Long historialTestId) {
        // Lógica para obtener el historial de test
        HistorialTest historialTest = historialTestRepository.findById(historialTestId)
                .orElseThrow(() -> new RuntimeException("Historial de test no encontrado."));

        // Aquí llamamos al método calcularFilaConMayorRespuestas
        Map<String, Object> resultados = respuestaService.calcularFilaConMayorRespuestas(historialTestId, historialTest.getTest().getId());

        // Asegúrate de que los resultados contengan los datos esperados
        if (!resultados.containsKey("Interes") || !resultados.containsKey("Aptitud")) {
            throw new RuntimeException("Los resultados del test no contienen los datos necesarios.");
        }

        return resultados; // Retorna el mapa con los resultados calculados
    }
}
