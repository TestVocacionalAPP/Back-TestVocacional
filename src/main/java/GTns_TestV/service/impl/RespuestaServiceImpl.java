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
    public Map<String, Object> calcularFilaConMayorRespuestas(Long idUsuario) {
        // Filtrar las respuestas del usuario actual
        List<Respuesta> respuestas = respuestaRepository.findByUsuarioId(idUsuario);

        // Inicializamos las sumas de cada categoría en CHASIDE para INTERES y APTITUD
        Map<String, Integer> sumaInteres = new HashMap<>();
        Map<String, Integer> sumaAptitud = new HashMap<>();

        // Inicializar las sumas en 0 para cada letra de CHASIDE
        Arrays.asList("C", "H", "A", "S", "I", "D", "E").forEach(letra -> {
            sumaInteres.put(letra, 0);
            sumaAptitud.put(letra, 0);
        });

        // Iterar sobre todas las respuestas y sumar el valor de acuerdo a la categoría de la pregunta
        for (Respuesta respuesta : respuestas) {
            Integer valor = respuesta.getValor();
            Integer numeroPregunta = respuesta.getPregunta().getIdPregunta().intValue(); // Número de la pregunta

            if (respuesta.getTipoPregunta() == TipoPregunta.INTERES) {
                // Sumamos las respuestas en función de la letra de CHASIDE correspondiente para INTERES
                for (Interes interes : Interes.values()) {
                    if (interes.getPreguntas().contains(numeroPregunta)) {
                        sumaInteres.put(interes.name(), sumaInteres.get(interes.name()) + valor);
                    }
                }
            } else if (respuesta.getTipoPregunta() == TipoPregunta.APTITUD) {
                // Sumamos las respuestas en función de la letra de CHASIDE correspondiente para APTITUD
                for (Aptitud aptitud : Aptitud.values()) {
                    if (aptitud.getPreguntas().contains(numeroPregunta)) {
                        sumaAptitud.put(aptitud.name(), sumaAptitud.get(aptitud.name()) + valor);
                    }
                }
            }
        }

        // Obtener la letra con mayor valor en INTERES y APTITUD
        String maxInteres = Collections.max(sumaInteres.entrySet(), Map.Entry.comparingByValue()).getKey();
        String maxAptitud = Collections.max(sumaAptitud.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Determinar cuál tiene el mayor valor general
        String tipoMayorCategoria = sumaInteres.get(maxInteres) >= sumaAptitud.get(maxAptitud) ? "Interes" : "Aptitud";

        // Devolvemos las sumas y la categoría mayor
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("Interes", sumaInteres);  // Suma para cada letra de CHASIDE en INTERES
        resultado.put("Aptitud", sumaAptitud);  // Suma para cada letra de CHASIDE en APTITUD
        resultado.put("CategoriaMayorInteres", maxInteres);
        resultado.put("CategoriaMayorAptitud", maxAptitud);
        resultado.put("TipoMayorCategoria", tipoMayorCategoria);

        return resultado;
    }
}
