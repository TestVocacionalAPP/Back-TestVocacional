package GTns_TestV.controller;

import GTns_TestV.infra.repository.PreguntaRepository;
import GTns_TestV.infra.repository.TestRepository;
import GTns_TestV.model.dto.test.TestCreationDTO;
import GTns_TestV.model.dto.test.TestResponseDTO;
import GTns_TestV.model.entity.Pregunta;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.enums.TipoPregunta;
import GTns_TestV.service.TestService;
import GTns_TestV.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final UsuarioService usuarioService;  // Inyectamos UsuarioService
    private final TestRepository testRepository;  // Asegúrate de que esté inyectado correctamente
    private final PreguntaRepository preguntaRepository;

    @PostMapping("/crear")
    public ResponseEntity<TestResponseDTO> crearTest(@RequestBody TestCreationDTO testCreationDTO) {
        // Obtenemos el usuario autenticado
        Usuario usuario = usuarioService.getAuthenticatedUser();

        // Llamamos al servicio con el DTO y el ID del usuario autenticado
        TestResponseDTO nuevoTest = testService.crearTest(testCreationDTO, usuario.getId());

        return ResponseEntity.ok(nuevoTest);
    }
}
