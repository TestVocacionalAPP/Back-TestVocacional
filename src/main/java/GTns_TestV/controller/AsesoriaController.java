package GTns_TestV.controller;

import GTns_TestV.model.dto.asesoria.AsesoriaCreateDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaResponseDTO;
import GTns_TestV.model.entity.Experto;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.security.JwtService;
import GTns_TestV.service.AsesoriaService;
import GTns_TestV.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asesorias")
public class AsesoriaController {

    @Autowired
    private AsesoriaService asesoriaService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/solicitar")
    public ResponseEntity<AsesoriaResponseDTO> solicitarAsesoria(
            @RequestBody AsesoriaCreateDTO asesoriaCreateDTO) {
        Usuario usuarioAutenticado = usuarioService.getAuthenticatedUser();
        AsesoriaResponseDTO nuevaAsesoria = asesoriaService.solicitarAsesoria(asesoriaCreateDTO, usuarioAutenticado.getId());
        return ResponseEntity.ok(nuevaAsesoria);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AsesoriaResponseDTO>> obtenerAsesoriasPorUsuario(@PathVariable Long usuarioId) {
        List<AsesoriaResponseDTO> asesorias = asesoriaService.obtenerAsesoriasPorUsuario(usuarioId);
        return ResponseEntity.ok(asesorias);
    }

    @GetMapping("/experto/solicitudes")
    public ResponseEntity<List<AsesoriaResponseDTO>> obtenerSolicitudesDeAsesoriaParaExperto() {
        Usuario expertoAutenticado = usuarioService.getAuthenticatedUser();
        if (!(expertoAutenticado instanceof Experto)) {
            throw new RuntimeException("El usuario autenticado no es un experto.");
        }
        List<AsesoriaResponseDTO> asesorias = asesoriaService.obtenerAsesoriasPorExperto(expertoAutenticado.getId());
        return ResponseEntity.ok(asesorias);
    }

    @PostMapping("/confirmar/{asesoriaId}")
    public ResponseEntity<AsesoriaResponseDTO> confirmarAsesoria(@PathVariable Long asesoriaId) {
        // Obtén el usuario autenticado
        Usuario expertoAutenticado = usuarioService.getAuthenticatedUser();

        // Verifica si el usuario es un experto y obtiene su ID
        if (!(expertoAutenticado instanceof Experto)) {
            throw new RuntimeException("El usuario autenticado no es un experto.");
        }

        Long expertoId = expertoAutenticado.getId();  // Asegúrate de que `getId()` devuelva el ID del experto

        // Llama al servicio con el `expertoId` obtenido
        AsesoriaResponseDTO confirmada = asesoriaService.confirmarAsesoria(asesoriaId, expertoId);
        return ResponseEntity.ok(confirmada);
    }


    @PostMapping("/rechazar/{asesoriaId}")
    public ResponseEntity<AsesoriaResponseDTO> rechazarSolicitud(@PathVariable Long asesoriaId) {
        Usuario expertoAutenticado = usuarioService.getAuthenticatedUser();
        if (!(expertoAutenticado instanceof Experto)) {
            throw new RuntimeException("El usuario autenticado no es un experto.");
        }

        Long expertoId = expertoAutenticado.getId();
        AsesoriaResponseDTO rechazada = asesoriaService.rechazarSolicitud(asesoriaId, expertoId);
        return ResponseEntity.ok(rechazada);
    }

    @GetMapping("/verificar")
    public ResponseEntity<List<String>> verificarYNotificarCitas() {
        // Obtiene el usuario autenticado
        Usuario usuarioAutenticado = usuarioService.getAuthenticatedUser();

        // Llama al servicio para verificar y obtener notificaciones
        List<String> notificaciones = asesoriaService.verificarYNotificarCitas(usuarioAutenticado.getId());

        // Retorna las notificaciones como respuesta
        return ResponseEntity.ok(notificaciones);
    }
}
