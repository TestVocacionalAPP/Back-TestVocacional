package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.asesoria.AsesoriaCreateDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaResponseDTO;
import GTns_TestV.model.dto.asesoria.AsesoriaUpdateDTO;
import GTns_TestV.model.entity.Asesoria;
import GTns_TestV.model.entity.Experto;
import GTns_TestV.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AsesoriaMapper {

    public AsesoriaResponseDTO toResponseDTO(Asesoria asesoria) {
        return new AsesoriaResponseDTO(
                asesoria.getId(),
                asesoria.getAsunto(),
                asesoria.getFechaSolicitada(),
                asesoria.getFechaConfirmada(),
                asesoria.getEstado()
        );
    }

    public Asesoria toEntity(AsesoriaCreateDTO createDTO, Usuario usuario, Experto experto) {
        Asesoria asesoria = new Asesoria();
        asesoria.setUsuario(usuario);
        asesoria.setExperto(experto);
        asesoria.setAsunto(createDTO.getAsunto());
        asesoria.setFechaSolicitada(createDTO.getFechaSolicitada());
        asesoria.setEstado("PENDIENTE"); // Estado inicial al crear la asesoría
        return asesoria;
    }

    public Asesoria updateFromDTO(AsesoriaUpdateDTO updateDTO, Asesoria asesoria) {
        asesoria.setFechaConfirmada(updateDTO.getFechaConfirmada());
        asesoria.setEstado(updateDTO.getEstado());
        return asesoria;
    }

}
