package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.PreguntaRepository;
import GTns_TestV.infra.repository.UsuarioRepository;
import GTns_TestV.model.dto.mapper.TestMapper;

import GTns_TestV.model.dto.test.TestCreationDTO;
import GTns_TestV.model.dto.test.TestResponseDTO;
import GTns_TestV.model.dto.test.TestUpdateDTO;
import GTns_TestV.model.entity.Test;
import GTns_TestV.model.entity.Pregunta;
import GTns_TestV.infra.repository.TestRepository;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.enums.TipoPregunta;
import GTns_TestV.service.TestService;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final UsuarioRepository usuarioRepository;
    private final TestMapper testMapper;
    private final TestRepository testRepository;
    private final PreguntaRepository preguntaRepository;

    @Override
    @Transactional
    public TestResponseDTO crearTest(TestCreationDTO testCreationDTO, Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Test test = testMapper.toEntity(testCreationDTO, usuario);
        Test savedTest = testRepository.save(test);
        return testMapper.toResponseDTO(savedTest);
    }

    @Override
    @Transactional
    public TestResponseDTO actualizarTest(Long idTest, TestUpdateDTO testUpdateDTO) {
        Test testExistente = testRepository.findById(idTest)
                .orElseThrow(() -> new RuntimeException("Test no encontrado"));

        testMapper.updateEntity(testUpdateDTO, testExistente);
        Test updatedTest = testRepository.save(testExistente);

        return testMapper.toResponseDTO(updatedTest);
    }

    @Override
    public List<TestResponseDTO> obtenerTodosLosTests() {
        List<Test> tests = testRepository.findAll();
        return tests.stream()
                .map(testMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Test obtenerTestPorId(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test no encontrado")); // Retorna directamente el Test
    }
    @Override
    public List<Pregunta> obtenerPreguntasPorTest(Long idTest) {
        // Buscar el test por ID
        Test test = testRepository.findById(idTest)
                .orElseThrow(() -> new RuntimeException("Test no encontrado"));

        // Obtener las preguntas asociadas a ese test
        return preguntaRepository.findByTest(test);
    }

    @Override
    public List<TestResponseDTO> obtenerTodosLosTestsPorUsuario(Long idUsuario) {
        List<Test> tests = testRepository.findAllByUsuarioId(idUsuario);
        return tests.stream()
                .map(testMapper::toResponseDTO)
                .toList();
    }

    @Override
    public TestResponseDTO obtenerTestPorIdYUsuario(Long idTest, Long idUsuario) {
        Test test = testRepository.findByIdAndUsuarioId(idTest, idUsuario)
                .orElseThrow(() -> new RuntimeException("Test no encontrado para este usuario."));
        return testMapper.toResponseDTO(test);
    }

    @Override
    @Transactional
    public void cargarPreguntasDesdeCSV(MultipartFile file, Long idTest, Long idUsuario) {
        // Buscar el test por id y usuario
        Test test = testRepository.findByIdAndUsuarioId(idTest, idUsuario)
                .orElseThrow(() -> new RuntimeException("Test no encontrado o no pertenece al usuario"));

        List<Pregunta> preguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            // Ignorar la primera línea (encabezado)
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Verificar y procesar cada línea
                if (line.trim().isEmpty()) {
                    continue;  // Ignorar líneas vacías
                }
                String[] values = line.split(",", 2);  // Dividir solo en la primera coma
                if (values.length != 2) {
                    throw new RuntimeException("Formato de línea inválido en el archivo CSV: " + line);
                }
                try {
                    Pregunta pregunta = new Pregunta();
                    pregunta.setEnunciado(values[0].trim().replace("\"", "")); // Remover comillas si las hay
                    pregunta.setPuntajePregunta(Integer.parseInt(values[1].trim()));
                    pregunta.setTest(test);  // Asociar la pregunta con el test
                    preguntas.add(pregunta);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("El puntaje no es un número válido en la línea: " + line, e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo CSV", e);
        }

        preguntaRepository.saveAll(preguntas);
    }
    @Override
    @Transactional
    public TestResponseDTO crearTestConPreguntas(String titulo, Long idUsuario, MultipartFile file) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear el test
        Test test = new Test();
        test.setTitulo(titulo);
        test.setUsuario(usuario);
        Test savedTest = testRepository.save(test);

        // Procesar el archivo Excel y cargar las preguntas
        List<Pregunta> preguntas = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell preguntaCell = row.getCell(0);
                Cell categoriaCell = row.getCell(1);
                Cell tipoPreguntaCell = row.getCell(2);

                if (preguntaCell != null && categoriaCell != null && tipoPreguntaCell != null) {
                    String preguntaTexto = preguntaCell.getStringCellValue().trim();
                    String categoria = categoriaCell.getStringCellValue().trim().toUpperCase();
                    String tipoPreguntaStr = tipoPreguntaCell.getStringCellValue().trim().toUpperCase();

                    try {
                        TipoPregunta tipo = TipoPregunta.valueOf(tipoPreguntaStr);
                        if (!List.of("C", "H", "A", "S", "I", "D", "E").contains(categoria)) {
                            throw new RuntimeException("Categoría inválida en la fila: " + (row.getRowNum() + 1));
                        }

                        Pregunta pregunta = new Pregunta();
                        pregunta.setEnunciado(preguntaTexto);
                        pregunta.setTest(savedTest);
                        pregunta.setTipoPregunta(tipo);
                        pregunta.setCategoria(categoria);

                        preguntas.add(pregunta);

                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Tipo de pregunta inválido en la fila: " + (row.getRowNum() + 1));
                    }
                }
            }

            if (!preguntas.isEmpty()) {
                preguntaRepository.saveAll(preguntas);
            } else {
                throw new RuntimeException("No se encontraron preguntas válidas para cargar.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo Excel", e);
        }

        return testMapper.toResponseDTO(savedTest);
    }

}

