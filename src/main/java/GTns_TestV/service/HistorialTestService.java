package GTns_TestV.service;

import GTns_TestV.model.dto.historial.HistorialResponseDTO;
import GTns_TestV.model.entity.HistorialTest;
import GTns_TestV.model.entity.Usuario;
import GTns_TestV.model.entity.Test;

import java.util.List;
import java.util.Map;

public interface HistorialTestService {
    List<HistorialResponseDTO> obtenerHistorialPorUsuario(Long idUsuario);
}
