package GTns_TestV.controller;

import GTns_TestV.model.entity.Carrera;
import GTns_TestV.service.CarreraService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carreras")
@RequiredArgsConstructor
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @GetMapping("/carreras")
    public ResponseEntity<List<Carrera>> listarCarreras() {
        List<Carrera> carreras = carreraService.listarCarreras();
        return ResponseEntity.ok(carreras);
    }
}
