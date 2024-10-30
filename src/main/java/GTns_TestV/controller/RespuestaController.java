package GTns_TestV.controller;

import GTns_TestV.infra.repository.TestRepository;
import GTns_TestV.model.dto.RespuestaDTO;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.RespuestaService;
import GTns_TestV.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TestRepository testRepository;

    // Endpoint para calcular la fila con mayor respuestas usando el usuario autenticado desde el token
    @GetMapping("/calcular/{historialTestId}")
    public ResponseEntity<Map<String, Object>> calcularResultadosChaside(@PathVariable Long historialTestId) {
        Map<String, Object> resultados = respuestaService.calcularFilaConMayorRespuestas(historialTestId);
        return ResponseEntity.ok(resultados);
    }



    @PostMapping("/responder")
    public ResponseEntity<String> responderCuestionario(@RequestBody List<RespuestaDTO> respuestasDTO, @RequestParam Long idTest) {
        Usuario usuario = usuarioService.getAuthenticatedUser();
        Test test = testRepository.findById(idTest)
                .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + idTest));

        respuestaService.procesarRespuestas(usuario, test, respuestasDTO); // Llamada al servicio

        return ResponseEntity.ok("Respuestas guardadas correctamente");
    }

}
