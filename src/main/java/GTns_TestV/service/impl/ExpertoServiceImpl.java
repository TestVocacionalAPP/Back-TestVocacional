package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.ExpertoRepository;
import GTns_TestV.model.dto.experto.ExpertoCreateDTO;
import GTns_TestV.model.dto.experto.ExpertoResponseDTO;
import GTns_TestV.model.dto.experto.ExpertoUpdateDTO;
import GTns_TestV.model.dto.mapper.ExpertoMapper;
import GTns_TestV.model.entity.Experto;

import GTns_TestV.service.ExpertoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpertoServiceImpl implements ExpertoService {

    private final ExpertoRepository expertoRepository; // Repositorio para manejar Expertos
    private final ExpertoMapper expertoMapper; // Mapper para convertir entre DTOs y entidades

    @Override
    public ExpertoResponseDTO crearExperto(ExpertoCreateDTO expertoCreateDTO) {
        Experto experto = expertoMapper.toEntity(expertoCreateDTO); // Convierte DTO a entidad
        Experto savedExperto = expertoRepository.save(experto); // Guarda el experto
        return expertoMapper.toResponseDTO(savedExperto); // Convierte la entidad guardada a DTO
    }

    @Override
    public ExpertoResponseDTO actualizarExperto(Long id, ExpertoUpdateDTO expertoUpdateDTO) {
        Experto experto = expertoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experto no encontrado")); // Lanza excepción si no se encuentra

        expertoMapper.updateEntity(expertoUpdateDTO, experto); // Actualiza la entidad con los datos del DTO
        Experto updatedExperto = expertoRepository.save(experto); // Guarda los cambios
        return expertoMapper.toResponseDTO(updatedExperto); // Convierte la entidad actualizada a DTO
    }

    @Override
    public List<ExpertoResponseDTO> obtenerTodosLosExpertos() {
        List<Experto> expertos = expertoRepository.findAll(); // Obtiene todos los expertos
        return expertos.stream()
                .map(expertoMapper::toResponseDTO) // Convierte cada experto a DTO
                .collect(Collectors.toList());
    }

    @Override
    public ExpertoResponseDTO obtenerExpertoPorId(Long id) {
        Experto experto = expertoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experto no encontrado")); // Lanza excepción si no se encuentra
        return expertoMapper.toResponseDTO(experto); // Convierte la entidad a DTO
    }

    @Override
    public void eliminarExperto(Long id) {
        expertoRepository.deleteById(id); // Elimina el experto por ID
    }
}
