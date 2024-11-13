package GTns_TestV.controller;

import GTns_TestV.infra.repository.ExpertoRepository;
import GTns_TestV.model.dto.experto.ExpertoCreateDTO;
import GTns_TestV.model.dto.experto.ExpertoPerfilDTO;
import GTns_TestV.model.dto.experto.ExpertoUpdateDTO;
import GTns_TestV.model.dto.experto.ExpertoResponseDTO;
import GTns_TestV.model.dto.mapper.ExpertoMapper;
import GTns_TestV.model.entity.Experto;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.service.ExpertoService;
import GTns_TestV.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expertos")
public class ExpertoController {

    @Autowired
    private ExpertoService expertoService;
    @Autowired
    private ExpertoRepository expertoRepository;
    @Autowired
    private ExpertoMapper expertoMapper;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExpertoResponseDTO> crearExperto(@RequestBody ExpertoCreateDTO expertoCreateDTO) {
        ExpertoResponseDTO nuevoExperto = expertoService.crearExperto(expertoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoExperto);
    }

    @GetMapping("/todos")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ExpertoResponseDTO>> obtenerTodosLosExpertos() {
        List<ExpertoResponseDTO> expertos = expertoService.obtenerTodosLosExpertos();
        return ResponseEntity.ok(expertos);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ExpertoResponseDTO> actualizarExperto(@PathVariable Long id, @RequestBody ExpertoUpdateDTO expertoUpdateDTO) {
        ExpertoResponseDTO expertoActualizado = expertoService.actualizarExperto(id, expertoUpdateDTO);
        return ResponseEntity.ok(expertoActualizado);
    }


    @GetMapping("/obtenerExpertoPorId/{id}")
    public ResponseEntity<ExpertoResponseDTO> obtenerExpertoPorId(@PathVariable Long id) {
        ExpertoResponseDTO experto = expertoService.obtenerExpertoPorId(id);
        return ResponseEntity.ok(experto);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/eliminarExperto/{id}")
    public ResponseEntity<Void> eliminarExperto(@PathVariable Long id) {
        expertoService.eliminarExperto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarPorEspecialidad")
    public ResponseEntity<List<ExpertoResponseDTO>> buscarPorEspecialidad(@RequestParam String especialidad) {
        List<ExpertoResponseDTO> expertos = expertoService.buscarExpertosPorEspecialidad(especialidad);
        return ResponseEntity.ok(expertos);
    }

    @PutMapping("/{expertoId}/toggleLike")
    public ResponseEntity<ExpertoResponseDTO> toggleLike(@PathVariable Long expertoId) {
        Usuario usuarioAutenticado = usuarioService.getAuthenticatedUser();
        ExpertoResponseDTO expertoActualizado = expertoService.toggleLike(expertoId, usuarioAutenticado.getId());
        return ResponseEntity.ok(expertoActualizado);
    }

    @GetMapping("/perfil")
    public ResponseEntity<ExpertoPerfilDTO> obtenerPerfilExpertoAutenticado() {
        ExpertoPerfilDTO perfil = expertoService.obtenerPerfilExperto(null);
        return ResponseEntity.ok(perfil);
    }

    @PutMapping("/editar/perfil")
    public ResponseEntity<ExpertoPerfilDTO> actualizarPerfilExperto(@RequestBody ExpertoPerfilDTO expertoPerfilDTO) {
        ExpertoPerfilDTO perfilActualizado = expertoService.actualizarPerfilExperto(expertoPerfilDTO);
        return ResponseEntity.ok(perfilActualizado);
    }
    @PutMapping("/perfil/imagen")
    public ResponseEntity<Map<String, String>> actualizarImagenPerfil(@RequestBody Map<String, String> request) {
        String imagenBase64 = request.get("imagenBase64");
        expertoService.actualizarImagenPerfil(imagenBase64);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Imagen de perfil actualizada exitosamente");
        return ResponseEntity.ok(response);
    }



}
