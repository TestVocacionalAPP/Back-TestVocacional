package GTns_TestV.controller;

import GTns_TestV.model.dto.PagoDTO;
import GTns_TestV.model.dto.recurso.RecursoCreateDTO;
import GTns_TestV.model.dto.recurso.RecursoResponseDTO;
import GTns_TestV.service.RecursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<RecursoResponseDTO> actualizarRecurso(
            @PathVariable Long id,
            @RequestBody RecursoCreateDTO recursoCreateDTO) {
        RecursoResponseDTO recursoActualizado = recursoService.actualizarRecurso(id, recursoCreateDTO);
        return ResponseEntity.ok(recursoActualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarRecurso(@PathVariable Long id) {
        recursoService.eliminarRecurso(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idRecurso}/comprar")
    public ResponseEntity<RecursoResponseDTO> comprarRecurso(@PathVariable Long idRecurso, @RequestBody PagoDTO pagoDTO) {
        try {
            RecursoResponseDTO recursoComprado = recursoService.comprarRecurso(idRecurso, pagoDTO);
            return ResponseEntity.ok(recursoComprado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Error de compra ya realizada o recurso gratuito
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Error de recurso no encontrado
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Otro error
        }
    }

}
