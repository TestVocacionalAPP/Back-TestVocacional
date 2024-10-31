package GTns_TestV.model.dto.experto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertoUpdateDTO {
    private String especialidad;
    private String descripcion;
    private double tarifa;
}
