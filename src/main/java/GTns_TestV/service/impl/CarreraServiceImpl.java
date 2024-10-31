package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.CarreraRepository;
import GTns_TestV.model.entity.Carrera;
import GTns_TestV.service.CarreraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarreraServiceImpl implements CarreraService {

    private final CarreraRepository carreraRepository;

    @Override
    public List<Carrera> obtenerCarrerasCompatibles(String categoriaInteres, String categoriaAptitud) {
        // Obtener todas las carreras
        List<Carrera> carreras = carreraRepository.findAll();

        // Filtrar carreras por categorías de interés y aptitud
        return carreras.stream()
                .filter(carrera ->
                        carrera.getCategoria().name().equals(categoriaInteres) ||
                                carrera.getCategoria().name().equals(categoriaAptitud))
                .collect(Collectors.toList());
    }
}
