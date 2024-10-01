package GTns_TestV.controller;

import GTns_TestV.infra.repository.PreguntaRepository;
import GTns_TestV.infra.repository.RespuestaRepository;
import GTns_TestV.infra.repository.TestRepository;
import GTns_TestV.model.dto.RespuestaDTO;
import GTns_TestV.model.dto.test.TestCreationDTO;
import GTns_TestV.model.dto.test.TestResponseDTO;
import GTns_TestV.model.entity.Pregunta;
import GTns_TestV.model.entity.Respuesta;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.enums.Aptitud;
import GTns_TestV.model.enums.Interes;
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
    private final RespuestaRepository respuestaRepository;

    @PostMapping("/crear")
    public ResponseEntity<TestResponseDTO> crearTest(@RequestBody TestCreationDTO testCreationDTO) {
        // Obtenemos el usuario autenticado
        Usuario usuario = usuarioService.getAuthenticatedUser();

        // Llamamos al servicio con el DTO y el ID del usuario autenticado
        TestResponseDTO nuevoTest = testService.crearTest(testCreationDTO, usuario.getId());

        return ResponseEntity.ok(nuevoTest);
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file, @RequestParam("idTest") Long idTest) {
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
                        pregunta.setTest(test);
                        pregunta.setTipoPregunta(tipo); // Asignar el tipo de pregunta

                        // Asignar la categoría
                        pregunta.setCategoria(categoria);

                        preguntas.add(pregunta); // Agregar la pregunta a la lista

                    } catch (IllegalArgumentException e) {
                        // Si el tipo de pregunta no es válido, lo reportamos
                        return ResponseEntity.badRequest().body("Tipo de pregunta inválido en la fila: " + (row.getRowNum() + 1) + ". Valor: " + tipoPreguntaStr);
                    }
                }
            }

            // Guardar todas las preguntas en la base de datos
            preguntaRepository.saveAll(preguntas);

            workbook.close();
            return ResponseEntity.ok("Preguntas subidas correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error procesando el archivo");
        }
    }
    @PostMapping("/responder")
    public ResponseEntity<String> responderCuestionario(@RequestBody List<RespuestaDTO> respuestasDTO) {
        Usuario usuario = usuarioService.getAuthenticatedUser();

        List<Respuesta> respuestas = new ArrayList<>();

        for (RespuestaDTO respuestaDTO : respuestasDTO) {
            Pregunta pregunta = preguntaRepository.findById(respuestaDTO.getIdPregunta())
                    .orElseThrow(() -> new RuntimeException("Pregunta no encontrada. ID: " + respuestaDTO.getIdPregunta()));

            Respuesta respuesta = new Respuesta();
            respuesta.setPregunta(pregunta);
            respuesta.setUsuario(usuario);
            respuesta.setValor(respuestaDTO.getValor());

            // Asigna el tipo de pregunta (INTERES o APTITUD)
            respuesta.setTipoPregunta(pregunta.getTipoPregunta());

            // Verificar el tipo de pregunta y asignar la categoría adecuada
            if (pregunta.getTipoPregunta().equals(TipoPregunta.INTERES)) {
                try {
                    Interes interesEnum = Interes.valueOf(pregunta.getCategoria().toUpperCase());
                    respuesta.setInteres(interesEnum);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Categoría de interés inválida: " + pregunta.getCategoria());
                }
            } else if (pregunta.getTipoPregunta().equals(TipoPregunta.APTITUD)) {
                try {
                    Aptitud aptitudEnum = Aptitud.valueOf(pregunta.getCategoria().toUpperCase());
                    respuesta.setAptitud(aptitudEnum);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Categoría de aptitud inválida: " + pregunta.getCategoria());
                }
            }

            respuestas.add(respuesta);
        }

        // Guarda todas las respuestas en la base de datos
        respuestaRepository.saveAll(respuestas);

        return ResponseEntity.ok("Respuestas guardadas correctamente");
    }

}
