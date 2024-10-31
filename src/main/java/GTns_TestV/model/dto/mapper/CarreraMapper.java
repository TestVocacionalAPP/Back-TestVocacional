package GTns_TestV.model.dto.mapper;

import java.util.*;

public class CarreraMapper {
    private static final Map<String, List<String>> carreraMap = new HashMap<>();

    static {
        carreraMap.put("C", Arrays.asList("Administración de Empresas", "Gestión de Proyectos", "Contabilidad"));
        carreraMap.put("H", Arrays.asList("Psicología", "Derecho", "Sociología"));
        carreraMap.put("A", Arrays.asList("Diseño Gráfico", "Música", "Bellas Artes"));
        carreraMap.put("S", Arrays.asList("Medicina", "Enfermería", "Nutrición"));
        carreraMap.put("I", Arrays.asList("Ingeniería en Sistemas", "Desarrollo de Software", "Redes y Seguridad Informática"));
        carreraMap.put("D", Arrays.asList("Ciencias Policiales", "Gestión de Emergencias", "Defensa Civil"));
        carreraMap.put("E", Arrays.asList("Biología", "Química", "Ciencias Ambientales"));
    }

    public static List<String> obtenerCarreras(String categoria) {
        return carreraMap.getOrDefault(categoria, Collections.emptyList());
    }
}
