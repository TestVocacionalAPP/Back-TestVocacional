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
    private double calificacion;
    private String descripcion;
    private double tarifa;
}
