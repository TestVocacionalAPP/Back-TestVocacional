package GTns_TestV.service.impl;

import GTns_TestV.infra.repository.HistorialTestRepository;
import GTns_TestV.model.dto.historial.HistorialResponseDTO;
import GTns_TestV.model.entity.HistorialTest;
import GTns_TestV.service.HistorialTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialTestServiceImpl implements HistorialTestService {

    private final HistorialTestRepository historialTestRepository;

    @Override
    public List<HistorialResponseDTO> obtenerHistorialPorUsuario(Long idUsuario) {
        List<HistorialTest> historialTests = historialTestRepository.findByUsuarioId(idUsuario);

        return historialTests.stream().map(historialTest -> new HistorialResponseDTO(
                historialTest.getId(),
                historialTest.getTest().getId(),
                historialTest.getTest().getTitulo(), // Corrige el método para obtener el título
                historialTest.getFecha(),
                historialTest.getCategoriaMayorInteres(),
                historialTest.getCategoriaMayorAptitud(),
                historialTest.getMensajeIntereses(),
                historialTest.getMensajeCarreras()
        )).collect(Collectors.toList());
    }


}
