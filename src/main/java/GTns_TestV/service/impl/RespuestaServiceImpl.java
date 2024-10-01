package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.RespuestaRepository;
import GTns_TestV.model.entity.Respuesta;
import GTns_TestV.model.enums.TipoPregunta;
import GTns_TestV.model.enums.Interes;
import GTns_TestV.model.enums.Aptitud;
import GTns_TestV.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    public Map<String, Object> calcularFilaConMayorRespuestas() {
        List<Respuesta> respuestas = respuestaRepository.findAll();

        // Mapas para almacenar la suma de respuestas por categoría para intereses y aptitudes
        Map<Interes, Integer> sumaPorInteres = new HashMap<>();
        Map<Aptitud, Integer> sumaPorAptitud = new HashMap<>();

        // Inicializar los mapas de categorías con valor 0 para intereses y aptitudes
        for (Interes interes : Interes.values()) {
            sumaPorInteres.put(interes, 0);
        }
        for (Aptitud aptitud : Aptitud.values()) {
            sumaPorAptitud.put(aptitud, 0);
        }

        // Iterar sobre todas las respuestas y sumar el valor según el tipo de pregunta
        for (Respuesta respuesta : respuestas) {
            Integer valor = respuesta.getValor();

            if (respuesta.getTipoPregunta() == TipoPregunta.INTERES) {
                Interes interes = respuesta.getInteres();  // Obtenemos la categoría de interés
                sumaPorInteres.put(interes, sumaPorInteres.get(interes) + valor);
            } else if (respuesta.getTipoPregunta() == TipoPregunta.APTITUD) {
                Aptitud aptitud = respuesta.getAptitud();  // Obtenemos la categoría de aptitud
                sumaPorAptitud.put(aptitud, sumaPorAptitud.get(aptitud) + valor);
            }
        }

        // Encontrar la categoría con la mayor cantidad de respuestas afirmativas para intereses
        Map.Entry<Interes, Integer> interesMaximo = sumaPorInteres.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new RuntimeException("No hay respuestas de interés disponibles"));

        // Encontrar la categoría con la mayor cantidad de respuestas afirmativas para aptitudes
        Map.Entry<Aptitud, Integer> aptitudMaxima = sumaPorAptitud.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new RuntimeException("No hay respuestas de aptitud disponibles"));

        // Comparar los máximos de ambos tipos y determinar cuál tiene mayor valor
        String tipoMayorCategoria;
        if (interesMaximo.getValue() >= aptitudMaxima.getValue()) {
            tipoMayorCategoria = "Interes";
        } else {
            tipoMayorCategoria = "Aptitud";
        }

        // Retornar la categoría con el mayor valor (puedes ajustar esto para retornar lo que necesites)
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("CategoriaMayorInteres", interesMaximo.getKey());
        resultado.put("CategoriaMayorAptitud", aptitudMaxima.getKey());
        resultado.put("TipoMayorCategoria", tipoMayorCategoria);

        return resultado;
    }
}
