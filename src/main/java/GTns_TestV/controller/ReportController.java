package GTns_TestV.controller;

import GTns_TestV.service.ReportService;
import GTns_TestV.service.HistorialTestService;
import GTns_TestV.infra.repository.TestRepository;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
public class ReportController {
/*
    @Autowired
    private ReportService reportService;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UsuarioService userService;

    @Autowired
    private HistorialTestService historialTestService;

    // Endpoint para generar el reporte PDF
    @GetMapping("/report/{idTest}/{historialTestId}")
    public ResponseEntity<byte[]> generarReporte(@PathVariable Long idTest, @PathVariable Long historialTestId) {
        // Verificar si el test pertenece al usuario autenticado
        Test test = testRepository.findById(idTest)
                .orElseThrow(() -> new RuntimeException("Test no encontrado"));

        Usuario usuarioActual = userService.getAuthenticatedUser();

        // Verificar que el test pertenece al usuario autenticado
        if (!test.getUsuario().getId().equals(usuarioActual.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // Prohibido
        }

        // Obtener los resultados del test
        Map<String, Object> resultadoTest = historialTestService.obtenerResultadosDelTest(historialTestId);
        if (resultadoTest == null || resultadoTest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // No se encontraron resultados
        }

        // Generar el PDF
        ByteArrayInputStream pdfStream = reportService.generarReportePDF(resultadoTest);

        // Configurar las cabeceras para la respuesta
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=resultado_test.pdf");

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(pdfStream.readAllBytes());
    }*/
}
