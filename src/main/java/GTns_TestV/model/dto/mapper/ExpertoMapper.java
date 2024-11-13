package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.experto.ExpertoCreateDTO;
import GTns_TestV.model.dto.experto.ExpertoPerfilDTO;
import GTns_TestV.model.dto.experto.ExpertoResponseDTO;
import GTns_TestV.model.dto.experto.ExpertoUpdateDTO;
import GTns_TestV.model.entity.Experto;
import GTns_TestV.model.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class ExpertoMapper {

    public ExpertoResponseDTO toResponseDTO(Experto experto) {
        return new ExpertoResponseDTO(
                experto.getId(),
                experto.getNombre(),
                experto.getApellido(),
                experto.getEspecialidad(),
                experto.getLikes(),
                experto.getDescripcion(),
                experto.getTarifa(),
                experto.getTelefono(),
                experto.getCorreo(),
                experto.getImagenBase64()
        );
    }

    // Método para convertir un objeto Experto a ExpertoPerfilDTO
    public ExpertoPerfilDTO toPerfilDTO(Experto experto) {
        return new ExpertoPerfilDTO(
                experto.getNombre(),
                experto.getApellido(),
                experto.getEspecialidad(),
                experto.getDescripcion(),
                experto.getTarifa(),
                experto.getLikes(),
                experto.getImagenBase64(),
                experto.getCorreo(),
                experto.getTelefono()
        );
    }

    // Convertir ExpertoCreateDTO a entidad Experto
    public Experto toEntity(ExpertoCreateDTO expertoCreateDTO) {
        Experto experto = new Experto();
        experto.setNombre(expertoCreateDTO.getNombre());
        experto.setApellido(expertoCreateDTO.getApellido());
        experto.setTelefono(expertoCreateDTO.getTelefono());
        experto.setCorreo(expertoCreateDTO.getCorreo());
        experto.setPassword(expertoCreateDTO.getPassword());
        experto.setEspecialidad(expertoCreateDTO.getEspecialidad());
        experto.setDescripcion(expertoCreateDTO.getDescripcion());
        experto.setTarifa(expertoCreateDTO.getTarifa());
        experto.setLikes(0); // Inicializa la calificación a 0
        experto.setRole(Role.EXPERTO); // Asigna el rol EXPERTO
        return experto;
    }

    // Convertir ExpertoUpdateDTO a entidad Experto
    public void updateEntity(ExpertoUpdateDTO expertoUpdateDTO, Experto experto) {
        experto.setNombre(expertoUpdateDTO.getNombre());
        experto.setApellido(expertoUpdateDTO.getApellido());
        experto.setEspecialidad(expertoUpdateDTO.getEspecialidad());
        experto.setDescripcion(expertoUpdateDTO.getDescripcion());
        experto.setTarifa(expertoUpdateDTO.getTarifa());
    }
}
