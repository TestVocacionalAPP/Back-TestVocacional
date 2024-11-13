package GTns_TestV.controller;

import GTns_TestV.model.dto.CompraResponseDTO;
import GTns_TestV.service.RecursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final RecursoService recursoService;

    @GetMapping("/historial")
    public ResponseEntity<List<CompraResponseDTO>> obtenerHistorialCompras() {
        List<CompraResponseDTO> historialCompras = recursoService.obtenerHistorialCompras();
        return ResponseEntity.ok(historialCompras);
    }
}
