package GTns_TestV.controller;

import GTns_TestV.model.dto.asesoria.AsesoriaCreateDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaResponseDTO;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.security.JwtService;
import GTns_TestV.service.AsesoriaService;
import GTns_TestV.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/confirmar/{asesoriaId}")
    public ResponseEntity<AsesoriaResponseDTO> confirmarAsesoria(
            @PathVariable Long asesoriaId,
            @RequestParam Long expertoId) {
        AsesoriaResponseDTO confirmada = asesoriaService.confirmarAsesoria(asesoriaId, expertoId);
        return ResponseEntity.ok(confirmada);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AsesoriaResponseDTO>> obtenerAsesoriasPorUsuario(@PathVariable Long usuarioId) {
        List<AsesoriaResponseDTO> asesorias = asesoriaService.obtenerAsesoriasPorUsuario(usuarioId);
        return ResponseEntity.ok(asesorias);
    }

    @GetMapping("/experto/{expertoId}")
    public ResponseEntity<List<AsesoriaResponseDTO>> obtenerAsesoriasPorExperto(@PathVariable Long expertoId) {
        List<AsesoriaResponseDTO> asesorias = asesoriaService.obtenerAsesoriasPorExperto(expertoId);
        return ResponseEntity.ok(asesorias);
    }
}
