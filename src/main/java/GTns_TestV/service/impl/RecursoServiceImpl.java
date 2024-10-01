package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.RecursoRepository;
import GTns_TestV.model.dto.recurso.RecursoCreateDTO;
import GTns_TestV.model.dto.recurso.RecursoResponseDTO;
import GTns_TestV.model.entity.Recurso;
import GTns_TestV.model.dto.mapper.RecursoMapper;
import GTns_TestV.service.RecursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecursoServiceImpl implements RecursoService {

    private final RecursoRepository recursoRepository;
    private final RecursoMapper recursoMapper;

    @Override
    public RecursoResponseDTO crearRecurso(RecursoCreateDTO recursoCreateDTO) {
        Recurso recurso = recursoMapper.toEntity(recursoCreateDTO);
        Recurso recursoGuardado = recursoRepository.save(recurso);
        return recursoMapper.toResponseDTO(recursoGuardado);
    }

    @Override
    public List<RecursoResponseDTO> listarRecursos() {
        List<Recurso> recursos = recursoRepository.findAll();
        return recursos.stream()
                .map(recurso -> new RecursoResponseDTO(recurso.getId(), recurso.getTitulo(), recurso.getDescripcion(), recurso.getUrlRecurso()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RecursoResponseDTO> buscarRecursos(String titulo, String descripcion) {
        List<Recurso> recursos;

        if (titulo != null && descripcion != null) {
            recursos = recursoRepository.findByTituloContainingAndDescripcionContaining(titulo, descripcion);
        } else if (titulo != null) {
            recursos = recursoRepository.findByTituloContaining(titulo);
        } else if (descripcion != null) {
            recursos = recursoRepository.findByDescripcionContaining(descripcion);
        } else {
            recursos = recursoRepository.findAll(); // Si no se proveen filtros, devolver todos los recursos
        }

        return recursos.stream()
                .map(recurso -> new RecursoResponseDTO(recurso.getId(), recurso.getTitulo(), recurso.getDescripcion(), recurso.getUrlRecurso()))
                .collect(Collectors.toList());
    }
}
