package GTns_TestV.model.dto.experto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertoResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private int likes;
    private String descripcion;
    private double tarifa;
    private String telefono;
    private String correo;
}
