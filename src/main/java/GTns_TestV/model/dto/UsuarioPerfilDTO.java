package GTns_TestV.model.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class UsuarioPerfilDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @Pattern(regexp = "^\\d{9}$", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String correo;
}
