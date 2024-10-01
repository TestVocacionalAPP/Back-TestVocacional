package GTns_TestV.controller;

import GTns_TestV.model.dto.recurso.RecursoCreateDTO;
import GTns_TestV.model.dto.recurso.RecursoResponseDTO;
import GTns_TestV.service.RecursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
@RequiredArgsConstructor
public class RecursoController {

    private final RecursoService recursoService;

    // Crear un nuevo recurso educativo
    @PostMapping("/crear")
    public ResponseEntity<RecursoResponseDTO> crearRecurso(@RequestBody RecursoCreateDTO recursoCreateDTO) {
        RecursoResponseDTO recursoCreado = recursoService.crearRecurso(recursoCreateDTO);
        return ResponseEntity.ok(recursoCreado);
    }
    @GetMapping("/listar")
    public ResponseEntity<List<RecursoResponseDTO>> listarRecursos() {
        List<RecursoResponseDTO> recursos = recursoService.listarRecursos();
        return ResponseEntity.ok(recursos);
    }
    @GetMapping("/buscar")
    public ResponseEntity<List<RecursoResponseDTO>> buscarRecursos(@RequestParam(required = false) String titulo,
                                                                   @RequestParam(required = false) String descripcion) {
        List<RecursoResponseDTO> recursos = recursoService.buscarRecursos(titulo, descripcion);
        return ResponseEntity.ok(recursos);
    }
}
