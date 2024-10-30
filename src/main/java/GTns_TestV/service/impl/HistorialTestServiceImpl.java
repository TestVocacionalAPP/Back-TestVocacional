package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.HistorialTestRepository;
import GTns_TestV.model.entity.HistorialTest;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.entity.Test;
import GTns_TestV.service.HistorialTestService;
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
        // Obtener el historial de test usando Optional
        Optional<HistorialTest> optionalHistorialTest = historialTestRepository.findByUsuarioIdAndTestId(idUsuario, idTest);

        // Usar orElseThrow() sobre el Optional
        HistorialTest historialTest = optionalHistorialTest
                .orElseThrow(() -> new RuntimeException("No se encontró historial para el usuario y test especificados."));

        // Devolver los resultados como un mapa
        Map<String, Integer> resultados = new HashMap<>();
        resultados.put("Interes", historialTest.getInteres());
        resultados.put("Aptitud", historialTest.getAptitud());

        return resultados;
    }
}
