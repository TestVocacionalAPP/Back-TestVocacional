package GTns_TestV.model.dto.mapper;

import GTns_TestV.model.dto.experto.ExpertoCreateDTO;
import GTns_TestV.model.dto.experto.ExpertoResponseDTO;
import GTns_TestV.model.dto.experto.ExpertoUpdateDTO;
import GTns_TestV.model.entity.Experto;
import GTns_TestV.model.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class ExpertoMapper {

    // Convertir entidad Experto a ExpertoResponseDTO
    public ExpertoResponseDTO toResponseDTO(Experto experto) {
        return new ExpertoResponseDTO(
                experto.getId(),
                experto.getNombre(),
                experto.getApellido(),
                experto.getEspecialidad(),
                experto.getCalificacion(),
                experto.getDescripcion(),
                experto.getTarifa()
        );
    }

    // Convertir ExpertoCreateDTO a entidad Experto
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
        experto.setCalificacion(0); // Inicializa la calificación a 0
        experto.setRole(Role.EXPERTO); // Asigna el rol EXPERTO
        return experto;
    }


    // Convertir ExpertoUpdateDTO a entidad Experto
    public void updateEntity(ExpertoUpdateDTO expertoUpdateDTO, Experto experto) {
        experto.setEspecialidad(expertoUpdateDTO.getEspecialidad());
        experto.setDescripcion(expertoUpdateDTO.getDescripcion());
        experto.setTarifa(expertoUpdateDTO.getTarifa());
        // Puedes omitir la actualización de calificación si no es parte del DTO de actualización
    }
}
