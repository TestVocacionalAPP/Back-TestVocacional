package GTns_TestV.controller;

import GTns_TestV.model.entity.HistorialTest;
import GTns_TestV.service.ReportService;
import GTns_TestV.infra.repository.HistorialTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final HistorialTestRepository historialTestRepository;

    @GetMapping("/descargar/{historialTestId}")
    public ResponseEntity<InputStreamResource> descargarReporte(@PathVariable Long historialTestId) {
        HistorialTest historialTest = historialTestRepository.findById(historialTestId)
                .orElseThrow(() -> new RuntimeException("Historial de test no encontrado"));

        // Preparar los datos del resultado
        Map<String, Object> resultadoTest = new HashMap<>();
        resultadoTest.put("mensajeIntereses", historialTest.getMensajeIntereses());
        resultadoTest.put("mensajeCarreras", historialTest.getMensajeCarreras());

        ByteArrayInputStream bis = reportService.generarReportePDF(resultadoTest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=resultados_test.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
