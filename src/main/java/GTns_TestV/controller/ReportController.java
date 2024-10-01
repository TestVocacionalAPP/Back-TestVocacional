package GTns_TestV.controller;

import GTns_TestV.service.ReportService;
import GTns_TestV.service.RespuestaService;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final RespuestaService respuestaService;
    private final UsuarioService usuarioService; // Para obtener el usuario actual

    @GetMapping("/test/pdf")
    public ResponseEntity<InputStreamResource> generarReportePDF() {
        // Obtener el usuario autenticado
        Usuario usuarioActual = usuarioService.getAuthenticatedUser();

        // Calcular los resultados del test para ese usuario y transformar a Map<String, Map<String, Integer>>
        Map<String, Map<String, Integer>> resultadosTest = (Map<String, Map<String, Integer>>) (Map) respuestaService.calcularFilaConMayorRespuestas(usuarioActual.getId());

        // Generar el PDF
        ByteArrayInputStream pdfStream = reportService.generarReportePDF(resultadosTest);

        // Crear headers para la respuesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=resultado_test.pdf");

        // Devolver el archivo PDF como respuesta
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
