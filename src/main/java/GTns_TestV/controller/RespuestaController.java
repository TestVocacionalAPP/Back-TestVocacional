package GTns_TestV.controller;

import GTns_TestV.infra.repository.TestRepository;
import GTns_TestV.model.dto.respuesta.RespuestaDTO;
import GTns_TestV.model.entity.Carrera;
import GTns_TestV.model.entity.HistorialTest;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.RespuestaService;
import GTns_TestV.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    @GetMapping("/calcular/{idTest}/{historialTestId}")
    public ResponseEntity<Map<String, Object>> calcularResultadosChaside(
            @PathVariable Long idTest,
            @PathVariable Long historialTestId) {

        Map<String, Object> resultados = respuestaService.calcularFilaConMayorRespuestas(historialTestId, idTest);
        return ResponseEntity.ok(resultados);
    }
    @PostMapping("/responder")
    public ResponseEntity<Map<String, Object>> responderCuestionario(@RequestBody List<RespuestaDTO> respuestasDTO, @RequestParam Long idTest) {
        Usuario usuario = usuarioService.getAuthenticatedUser();
        Test test = testRepository.findById(idTest)
                .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + idTest));

        // Procesar las respuestas y obtener el historial de test
        HistorialTest historialTest = respuestaService.procesarRespuestas(usuario, test, respuestasDTO);

        // Crear un mapa de respuesta que incluya el mensaje y el ID del historial
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Respuestas guardadas correctamente");
        response.put("historialTestId", historialTest.getId());

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{historialTestId}/carreras-sugeridas")
    public List<Carrera> obtenerCarrerasSugeridas(@PathVariable Long historialTestId, @RequestParam Long idTest) {
        return respuestaService.obtenerCarrerasSugeridas(historialTestId, idTest);
    }
}
