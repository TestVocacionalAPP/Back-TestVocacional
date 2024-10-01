package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.AsesoriaDTO;
import GTns_TestV.model.dto.UsuarioDTO;
import GTns_TestV.model.entity.Asesoria;
import org.springframework.stereotype.Component;

@Component
public class AsesoriaMapper {

    public AsesoriaDTO toDTO(Asesoria asesoria) {
        return new AsesoriaDTO(
                asesoria.getId(),
                UsuarioDTO.builder()
                        .id(asesoria.getUsuario().getId())
                        .nombre(asesoria.getUsuario().getNombre())
                        .apellido(asesoria.getUsuario().getApellido())
                        .correo(asesoria.getUsuario().getCorreo())
                        .build(),
                UsuarioDTO.builder()
                        .id(asesoria.getExperto().getId())
                        .nombre(asesoria.getExperto().getNombre())
                        .apellido(asesoria.getExperto().getApellido())
                        .correo(asesoria.getExperto().getCorreo())
                        .build(),
                asesoria.getAsunto(),
                asesoria.getFechaSolicitada(),
                asesoria.getFechaConfirmada()
        );
    }

    public Asesoria toEntity(AsesoriaDTO dto) {
        Asesoria asesoria = new Asesoria();
        asesoria.setAsunto(dto.getAsunto());
        asesoria.setFechaSolicitada(dto.getFechaSolicitada());
        asesoria.setFechaConfirmada(dto.getFechaConfirmada());
        return asesoria;
    }
}
