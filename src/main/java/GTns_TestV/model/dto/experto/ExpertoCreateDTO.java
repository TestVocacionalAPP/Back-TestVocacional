package GTns_TestV.model.dto.experto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertoCreateDTO {
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String password;
    private String especialidad;
    private String descripcion;
    private double tarifa;
}
