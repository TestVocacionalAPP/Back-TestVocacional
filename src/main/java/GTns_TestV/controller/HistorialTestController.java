package GTns_TestV.controller;

import GTns_TestV.model.dto.test.TestResponseDTO;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.entity.Test;
import GTns_TestV.service.HistorialTestService;
import GTns_TestV.service.TestService;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // Asegúrate de incluir esta importación
import java.util.Map;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
public class HistorialTestController {

   /* private final HistorialTestService historialTestService;
    private final TestService testService; // Asegúrate de tener esto
    private final UsuarioService usuarioService;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarResultados(@RequestBody Map<String, Integer> resultados,
                                                    @RequestParam Long idTest) {
        // Obtener el test por ID
        Test test = testService.obtenerTestPorId(idTest); // Asegúrate de que este método devuelve un Test

        // Obtener el usuario autenticado
        Usuario usuario = usuarioService.getAuthenticatedUser(); // Obtener usuario autenticado

        // Guardar resultados en el historial
        historialTestService.guardarResultadosEnHistorial(usuario, test, resultados);

        return ResponseEntity.ok("Resultados guardados correctamente");
    }

    @GetMapping("/resultados")
    public ResponseEntity<Map<String, Integer>> obtenerResultados(@RequestParam Long idUsuario,
                                                                  @RequestParam Long idTest) {
        Map<String, Integer> resultados = historialTestService.obtenerResultadosHistoricos(idUsuario, idTest);
        return ResponseEntity.ok(resultados);
    }

*/
}
