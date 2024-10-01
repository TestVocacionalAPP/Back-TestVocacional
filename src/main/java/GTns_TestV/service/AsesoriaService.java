package GTns_TestV.service;

import GTns_TestV.model.dto.AsesoriaDTO;

public interface AsesoriaService {
    AsesoriaDTO solicitarAsesoria(Long usuarioId, AsesoriaDTO asesoriaDTO);
}
