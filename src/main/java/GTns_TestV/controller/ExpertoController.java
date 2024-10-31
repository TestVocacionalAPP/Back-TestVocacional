package GTns_TestV.controller;

import GTns_TestV.model.dto.experto.ExpertoCreateDTO;
import GTns_TestV.model.dto.experto.ExpertoUpdateDTO;
import GTns_TestV.model.dto.experto.ExpertoResponseDTO;
import GTns_TestV.service.ExpertoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expertos")
public class ExpertoController {

    @Autowired
    private ExpertoService expertoService;

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



}
