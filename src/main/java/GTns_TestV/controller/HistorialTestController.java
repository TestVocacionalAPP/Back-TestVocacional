package GTns_TestV.controller;

import GTns_TestV.model.dto.historial.HistorialResponseDTO;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.HistorialTestService;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
public class HistorialTestController {

    private final HistorialTestService historialTestService;
    private final UsuarioService usuarioService;

    @GetMapping("/usuario")
    public ResponseEntity<List<HistorialResponseDTO>> obtenerHistorialDelUsuario() {
        // Obtener el usuario autenticado
        Usuario usuario = usuarioService.getAuthenticatedUser();

        // Obtener el historial de tests del usuario
        List<HistorialResponseDTO> historial = historialTestService.obtenerHistorialPorUsuario(usuario.getId());

        // Retornar la respuesta con el historial
        return ResponseEntity.ok(historial);
    }
}
