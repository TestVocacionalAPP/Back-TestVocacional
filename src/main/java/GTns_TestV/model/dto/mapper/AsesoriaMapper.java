package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.asesoria.AsesoriaCreateDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaResponseDTO;
import GTns_TestV.model.entity.Asesoria;
import org.springframework.stereotype.Component;

@Component
public class AsesoriaMapper {

    public AsesoriaResponseDTO toResponseDTO(Asesoria asesoria) {
        return new AsesoriaResponseDTO(
                asesoria.getId(),
                asesoria.getAsunto(),
                asesoria.getFechaSolicitada(),
                asesoria.getFechaConfirmada(),
                asesoria.getUsuario().getNombre(),
                asesoria.getExperto().getNombre()
        );
    }

    public Asesoria toEntity(AsesoriaCreateDTO createDTO) {
        Asesoria asesoria = new Asesoria();
        asesoria.setAsunto(createDTO.getAsunto());
        asesoria.setFechaSolicitada(createDTO.getFechaSolicitada());
        return asesoria;
    }


}
