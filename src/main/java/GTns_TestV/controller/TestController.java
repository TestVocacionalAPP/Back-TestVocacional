package GTns_TestV.controller;

import GTns_TestV.infra.repository.HistorialTestRepository;
import GTns_TestV.infra.repository.PreguntaRepository;
import GTns_TestV.infra.repository.RespuestaRepository;
import GTns_TestV.infra.repository.TestRepository;

import GTns_TestV.model.dto.pregunta.PreguntaDTO;
import GTns_TestV.model.dto.test.TestConPreguntasDTO;
import GTns_TestV.model.dto.test.TestCreationDTO;
import GTns_TestV.model.dto.test.TestResponseDTO;
import GTns_TestV.model.entity.*;
import GTns_TestV.model.enums.TipoPregunta;
import GTns_TestV.service.PreguntaService;
import GTns_TestV.service.TestService;
import GTns_TestV.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    private final UsuarioService usuarioService;
    private final TestRepository testRepository;
    private final PreguntaRepository preguntaRepository;
    private final RespuestaRepository respuestaRepository;
    private final PreguntaService preguntaService;
    private final HistorialTestRepository historialTestRepository;

    @PostMapping("/crear")
    public ResponseEntity<TestResponseDTO> crearTest(@RequestBody TestCreationDTO testCreationDTO) {
        // Obtenemos el usuario autenticado
        Usuario usuario = usuarioService.getAuthenticatedUser();

        // Llamamos al servicio con el DTO y el ID del usuario autenticado
        TestResponseDTO nuevoTest = testService.crearTest(testCreationDTO, usuario.getId());

        return ResponseEntity.ok(nuevoTest);
    }

    @PostMapping("/upload-excel/{idTest}")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file,
                                                  @PathVariable Long idTest) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo está vacío");
        }
        try {
            // Verificar si el test existe
            Test test = testRepository.findById(idTest)
                    .orElseThrow(() -> new RuntimeException("Test no encontrado"));

            // Procesar el archivo Excel
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0); // Obtener la primera hoja del archivo Excel

            List<Pregunta> preguntas = new ArrayList<>();

            // Iterar sobre las filas del archivo Excel
            for (Row row : sheet) {
                // Obtener la primera celda que contiene la pregunta
                Cell preguntaCell = row.getCell(0);  // Primera columna (A)
                // Obtener la segunda celda que contiene la categoría (C, H, A, etc.)
                Cell categoriaCell = row.getCell(1);  // Segunda columna (B)
                // Obtener la tercera celda que contiene el tipo de pregunta (INTERES o APTITUD)
                Cell tipoPreguntaCell = row.getCell(2);  // Tercera columna (C)

                if (preguntaCell != null && categoriaCell != null && tipoPreguntaCell != null) {
                    String preguntaTexto = preguntaCell.getStringCellValue().trim();
                    String categoria = categoriaCell.getStringCellValue().trim().toUpperCase();  // C, H, A, S, etc.
                    String tipoPreguntaStr = tipoPreguntaCell.getStringCellValue().trim().toUpperCase();

                    try {
                        // Verificar si el tipo de pregunta es válido (INTERES o APTITUD)
                        TipoPregunta tipo = TipoPregunta.valueOf(tipoPreguntaStr);

                        // Verificar que la categoría es válida
                        if (!List.of("C", "H", "A", "S", "I", "D", "E").contains(categoria)) {
                            return ResponseEntity.badRequest().body("Categoría inválida en la fila: " + (row.getRowNum() + 1) + ". Valor: " + categoria);
                        }

                        // Crear la entidad Pregunta
                        Pregunta pregunta = new Pregunta();
                        pregunta.setEnunciado(preguntaTexto);
                        pregunta.setTest(test); // Aquí se asegura que la pregunta se asocie al test correcto
                        pregunta.setTipoPregunta(tipo); // Asignar el tipo de pregunta
                        pregunta.setCategoria(categoria); // Asignar la categoría

                        preguntas.add(pregunta); // Agregar la pregunta a la lista

                    } catch (IllegalArgumentException e) {
                        // Si el tipo de pregunta no es válido, lo reportamos
                        return ResponseEntity.badRequest().body("Tipo de pregunta inválido en la fila: " + (row.getRowNum() + 1) + ". Valor: " + tipoPreguntaStr);
                    }
                }
            }

            // Guardar todas las preguntas en la base de datos
            if (!preguntas.isEmpty()) {
                preguntaRepository.saveAll(preguntas);
            } else {
                return ResponseEntity.badRequest().body("No se encontraron preguntas válidas para cargar.");
            }

            workbook.close();
            return ResponseEntity.ok("Preguntas subidas correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error procesando el archivo: " + e.getMessage());
        }
    }

    @GetMapping("/{idTest}/preguntas")
    public ResponseEntity<TestConPreguntasDTO> obtenerPreguntasPorTest(@PathVariable Long idTest) {
        Test test = testService.obtenerTestPorId(idTest);
        List<PreguntaDTO> preguntas = testService.obtenerPreguntasPorTest(idTest).stream()
                .map(pregunta -> new PreguntaDTO(
                        pregunta.getIdPregunta(),
                        pregunta.getEnunciado(),
                        pregunta.getRespuestaSiNo(),
                        pregunta.getPuntajePregunta(),
                        pregunta.getTipoPregunta().name()
                ))
                .collect(Collectors.toList());

        TestConPreguntasDTO testConPreguntasDTO = TestConPreguntasDTO.builder()
                .id(test.getId())
                .titulo(test.getTitulo())
                .preguntas(preguntas)
                .build();

        return ResponseEntity.ok(testConPreguntasDTO);
    }
    @PostMapping("/crear-con-preguntas")
    public ResponseEntity<TestResponseDTO> crearTestConPreguntas(
            @RequestPart("titulo") String titulo,
            @RequestPart("file") MultipartFile file) {
        try {
            // Obtenemos el usuario autenticado
            Usuario usuario = usuarioService.getAuthenticatedUser();

            // Llamamos al servicio para crear el test y cargar las preguntas desde el archivo
            TestResponseDTO nuevoTest = testService.crearTestConPreguntas(titulo, usuario.getId(), file);

            return ResponseEntity.ok(nuevoTest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
