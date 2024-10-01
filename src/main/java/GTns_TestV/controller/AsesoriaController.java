package GTns_TestV.controller;

import GTns_TestV.model.dto.AsesoriaDTO;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.AsesoriaService;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asesorias")
@RequiredArgsConstructor
public class AsesoriaController {

    private final AsesoriaService asesoriaService;
    private final UsuarioService usuarioService;  // Para obtener el usuario autenticado

    @PostMapping("/solicitar")
    public ResponseEntity<String> solicitarAsesoria(@RequestBody AsesoriaDTO asesoriaDTO) {
        // Obtener el usuario autenticado
        Usuario usuarioActual = usuarioService.getAuthenticatedUser();

        // Pasar el ID del usuario autenticado y el AsesoriaDTO al servicio
        asesoriaService.solicitarAsesoria(usuarioActual.getId(), asesoriaDTO);

        // Devolver un mensaje de éxito
        return ResponseEntity.ok("Solicitud de asesoría enviada exitosamente.");
    }
}
