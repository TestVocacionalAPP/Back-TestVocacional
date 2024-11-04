package GTns_TestV.controller;

import GTns_TestV.infra.repository.ExpertoRepository;
import GTns_TestV.model.dto.experto.ExpertoCreateDTO;
import GTns_TestV.model.dto.experto.ExpertoUpdateDTO;
import GTns_TestV.model.dto.experto.ExpertoResponseDTO;
import GTns_TestV.model.dto.mapper.ExpertoMapper;
import GTns_TestV.model.entity.Experto;
import GTns_TestV.service.ExpertoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expertos")
public class ExpertoController {

    @Autowired
    private ExpertoService expertoService;
    private ExpertoRepository expertoRepository;
    private ExpertoMapper expertoMapper;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExpertoResponseDTO> crearExperto(@RequestBody ExpertoCreateDTO expertoCreateDTO) {
        ExpertoResponseDTO nuevoExperto = expertoService.crearExperto(expertoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoExperto);
    }

    @GetMapping("/todos")
    @PreAuthorize("hasRole('USER')")
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
}
