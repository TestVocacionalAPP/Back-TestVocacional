package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.CarreraRepository;
import GTns_TestV.infra.repository.HistorialTestRepository;
import GTns_TestV.infra.repository.PreguntaRepository;
import GTns_TestV.infra.repository.RespuestaRepository;
import GTns_TestV.model.dto.respuesta.RespuestaDTO;
import GTns_TestV.model.entity.*;
import GTns_TestV.model.enums.ChasideCategory;
import GTns_TestV.model.enums.TipoPregunta;
import GTns_TestV.model.enums.Interes;
import GTns_TestV.model.enums.Aptitud;
import GTns_TestV.service.CarreraService;
import GTns_TestV.service.RespuestaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RespuestaServiceImpl implements RespuestaService {
    @Autowired
    private final  @Lazy  RespuestaRepository respuestaRepository;
    @Autowired
    private final PreguntaRepository preguntaRepository;
    @Autowired
    private final HistorialTestRepository historialTestRepository;
    @Autowired
    private final  @Lazy CarreraService carreraService;
    @Autowired
    private final CarreraRepository carreraRepository;



    @Override
    @Transactional
    public HistorialTest procesarRespuestas(Usuario usuario, Test test, List<RespuestaDTO> respuestasDTO) {
        // Inicializar el historial de test
        HistorialTest historialTest = new HistorialTest();
        historialTest.setUsuario(usuario);
        historialTest.setTest(test);
        historialTest.setFecha(LocalDateTime.now());

        // Guardar el historialTest primero
        historialTest = historialTestRepository.save(historialTest);

        // Inicializar variables para los resultados
        int sumaInteres = 0;
        int sumaAptitud = 0;

        // Calcular los resultados de interés y aptitud
        for (RespuestaDTO respuestaDTO : respuestasDTO) {
            Pregunta pregunta = preguntaRepository.findById(respuestaDTO.getIdPregunta())
                    .orElseThrow(() -> new RuntimeException("Pregunta no encontrada. ID: " + respuestaDTO.getIdPregunta()));

            // Crear y guardar la respuesta
            Respuesta respuesta = new Respuesta();
            respuesta.setPregunta(pregunta);
            respuesta.setUsuario(usuario);
            respuesta.setTest(test);
            respuesta.setValor(respuestaDTO.getValor());
            respuesta.setHistorialTest(historialTest); // Aquí estableces la relación

            // Establecer el tipo de pregunta
            respuesta.setTipoPregunta(pregunta.getTipoPregunta());

            // Verificar y convertir el ID de la pregunta de Long a int
            Long idPreguntaLong = respuestaDTO.getIdPregunta();
            if (idPreguntaLong != null && idPreguntaLong <= Integer.MAX_VALUE && idPreguntaLong >= Integer.MIN_VALUE) {
                int idPreguntaInt = idPreguntaLong.intValue();

                if (pregunta.getTipoPregunta() == TipoPregunta.INTERES) {
                    sumaInteres += respuestaDTO.getValor();
                    respuesta.setInteres(Interes.fromPregunta(idPreguntaInt)); // Usar el ID de pregunta convertido
                } else if (pregunta.getTipoPregunta() == TipoPregunta.APTITUD) {
                    sumaAptitud += respuestaDTO.getValor();
                    respuesta.setAptitud(Aptitud.fromPregunta(idPreguntaInt)); // Usar el ID de pregunta convertido
                }

                respuestaRepository.save(respuesta);
            } else {
                throw new IllegalArgumentException("El ID de la pregunta está fuera del rango de un int: " + idPreguntaLong);
            }
        }

        // Llamar a calcularFilaConMayorRespuestas para obtener los mensajes generados
        Map<String, Object> resultadoCalculado = calcularFilaConMayorRespuestas(historialTest.getId(), test.getId());
        String mensajeIntereses = (String) resultadoCalculado.get("mensajeIntereses");
        String mensajeCarreras = (String) resultadoCalculado.get("mensajeCarreras");

        // Guardar los mensajes generados en el historial
        historialTest.setCategoriaMayorInteres((String) resultadoCalculado.get("CategoriaMayorInteres"));
        historialTest.setCategoriaMayorAptitud((String) resultadoCalculado.get("CategoriaMayorAptitud"));
        historialTest.setMensajeIntereses(mensajeIntereses);
        historialTest.setMensajeCarreras(mensajeCarreras);

        // Guardar el historial actualizado
        historialTestRepository.save(historialTest);

        return historialTest; // Retornar el historial guardado
    }

    private String obtenerCategoriaMayor(int suma) {
        // Método para determinar la categoría basada en la suma
        // Lógica de ejemplo: devolver una categoría ficticia según un valor
        if (suma > 15) {
            return "Categoría Alta";
        } else {
            return "Categoría Baja";
        }
    }


    public Map<String, Object> calcularFilaConMayorRespuestas(Long historialTestId, Long idTest) {
        HistorialTest historialTest = historialTestRepository.findById(historialTestId)
                .orElseThrow(() -> new RuntimeException("Historial de test no encontrado para el ID especificado."));

        List<Respuesta> respuestas = respuestaRepository.findByHistorialTestId(historialTest.getId());

        Map<String, Integer> sumaInteres = new HashMap<>();
        Map<String, Integer> sumaAptitud = new HashMap<>();
        List<String> categorias = Arrays.asList("C", "H", "A", "S", "I", "D", "E");

        for (String letra : categorias) {
            sumaInteres.put(letra, 0);
            sumaAptitud.put(letra, 0);
        }

        for (Respuesta respuesta : respuestas) {
            Integer valor = respuesta.getValor();
            int numeroPregunta = respuesta.getPregunta().getIdPregunta().intValue();

            if (respuesta.getTipoPregunta() == TipoPregunta.INTERES) {
                for (Interes interes : Interes.values()) {
                    if (interes.getPreguntas().contains(numeroPregunta)) {
                        sumaInteres.put(interes.name(), sumaInteres.get(interes.name()) + valor);
                    }
                }
            } else if (respuesta.getTipoPregunta() == TipoPregunta.APTITUD) {
                for (Aptitud aptitud : Aptitud.values()) {
                    if (aptitud.getPreguntas().contains(numeroPregunta)) {
                        sumaAptitud.put(aptitud.name(), sumaAptitud.get(aptitud.name()) + valor);
                    }
                }
            }
        }

        String maxInteres = Collections.max(sumaInteres.entrySet(), Map.Entry.comparingByValue()).getKey();
        String maxAptitud = Collections.max(sumaAptitud.entrySet(), Map.Entry.comparingByValue()).getKey();

        List<Carrera> carrerasInteres = carreraRepository.findByCategoria(ChasideCategory.valueOf(maxInteres));
        List<Carrera> carrerasAptitud = carreraRepository.findByCategoria(ChasideCategory.valueOf(maxAptitud));

        Set<Carrera> carrerasFinales = new HashSet<>(carrerasInteres);
        carrerasFinales.addAll(carrerasAptitud);

        String intereses = Arrays.stream(ChasideCategory.valueOf(maxInteres).getResultadosInteres())
                .map(Resultado::getNombre)
                .collect(Collectors.joining(", "));

        String aptitudes = Arrays.stream(ChasideCategory.valueOf(maxAptitud).getResultadosAptitud())
                .map(Resultado::getNombre)
                .collect(Collectors.joining(", "));

        String mensajeIntereses = String.format(
                "Tus intereses están relacionados con %s. Asimismo, tus aptitudes más destacadas se refieren a %s.",
                intereses, aptitudes
        );

        StringBuilder mensajeCarreras = new StringBuilder("Las carreras relacionadas a tus intereses y aptitudes son: ");
        for (Carrera carrera : carrerasFinales) {
            mensajeCarreras.append(carrera.getNombre()).append(", ");
        }
        if (!mensajeCarreras.isEmpty()) {
            mensajeCarreras.setLength(mensajeCarreras.length() - 2);
        }

        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("mensajeIntereses", mensajeIntereses);
        resultado.put("mensajeCarreras", mensajeCarreras.toString());
        resultado.put("Interes", ordenaResultados(sumaInteres, categorias));
        resultado.put("Aptitud", ordenaResultados(sumaAptitud, categorias));
        resultado.put("CategoriaMayorInteres", maxInteres);
        resultado.put("CategoriaMayorAptitud", maxAptitud);

        return resultado;
    }


    private Map<String, Integer> ordenaResultados(Map<String, Integer> resultados, List<String> categorias) {
        Map<String, Integer> ordenados = new LinkedHashMap<>();
        for (String categoria : categorias) {
            ordenados.put(categoria, resultados.get(categoria));
        }
        return ordenados;
    }

    public List<Carrera> obtenerCarrerasSugeridas(Long historialTestId, Long idTest) {
        // Calculamos las carreras sugeridas
        Map<String, Object> resultadoCalculado = calcularFilaConMayorRespuestas(historialTestId, idTest);
        String mensajeCarreras = (String) resultadoCalculado.get("mensajeCarreras");

        // Extraemos los nombres de las carreras del mensaje
        List<String> nombresCarreras = Arrays.stream(mensajeCarreras
                        .replace("Las carreras relacionadas a tus intereses y aptitudes son: ", "")
                        .split(", "))
                .collect(Collectors.toList());

        // Consultamos las carreras en la base de datos
        return carreraRepository.findByNombreIn(nombresCarreras);
    }


}
