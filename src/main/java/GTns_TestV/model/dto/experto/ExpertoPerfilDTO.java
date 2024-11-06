package GTns_TestV.model.dto.experto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpertoPerfilDTO {
    private String nombre;
    private String apellido;
    private String especialidad;
    private String descripcion;
    private double tarifa;
    private int likes;
    private String imagenBase64;
    private String correo;
    private String telefono;
}
