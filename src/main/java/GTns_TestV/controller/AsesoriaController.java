package GTns_TestV.controller;

import GTns_TestV.model.dto.asesoria.AsesoriaCreateDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaResponseDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaUpdateDTO;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.enums.Role;
import GTns_TestV.service.AsesoriaService;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asesorias")
@RequiredArgsConstructor
public class AsesoriaController {

    private final AsesoriaService asesoriaService;
    private final UsuarioService usuarioService;  // Para obtener el usuario autenticado

    // Endpoint para solicitar una asesoría
    @PostMapping("/solicitar")
    public ResponseEntity<String> solicitarAsesoria(@RequestBody AsesoriaCreateDTO asesoriaCreateDTO) {
        // Obtener el usuario autenticado
        Usuario usuarioActual = usuarioService.getAuthenticatedUser();

        // Pasar el ID del usuario autenticado y el AsesoriaCreateDTO al servicio
        asesoriaService.solicitarAsesoria(usuarioActual.getId(), asesoriaCreateDTO);

        // Devolver un mensaje de éxito
        return ResponseEntity.ok("Solicitud de asesoría enviada exitosamente.");
    }

    // Endpoint para listar solicitudes de asesoría para el experto
    @GetMapping("/experto/solicitudes")
    public ResponseEntity<List<AsesoriaResponseDTO>> listarSolicitudesParaExperto() {
        // Obtener el usuario autenticado
        Usuario expertoActual = usuarioService.getAuthenticatedUser();

        // Verificar si el usuario tiene el rol de EXPERTO
        if (!expertoActual.getRole().equals(Role.EXPERTO)) {
            return ResponseEntity.status(403).build(); // Retornar 403 Forbidden si no es experto
        }

        // Obtener las solicitudes de asesoría para el experto
        List<AsesoriaResponseDTO> solicitudes = asesoriaService.listarSolicitudesPorExperto(expertoActual.getId());

        // Retornar la lista de solicitudes
        return ResponseEntity.ok(solicitudes);
    }

    @PutMapping("/experto/actualizar-estado")
    public ResponseEntity<AsesoriaResponseDTO> actualizarEstadoAsesoria(@RequestBody AsesoriaUpdateDTO asesoriaUpdateDTO) {
        // Obtener el usuario autenticado (experto)
        Usuario expertoActual = usuarioService.getAuthenticatedUser();

        // Verificar que el usuario sea un experto
        if (!expertoActual.getRole().equals(Role.EXPERTO)) {
            return ResponseEntity.status(403).build();  // Retornar 403 Forbidden si no es experto
        }

        // Actualizar el estado de la asesoría
        AsesoriaResponseDTO respuesta = asesoriaService.actualizarEstadoAsesoria(expertoActual.getId(), asesoriaUpdateDTO);

        return ResponseEntity.ok(respuesta);
    }
}
