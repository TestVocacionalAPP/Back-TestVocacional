package GTns_TestV.model.dto.recurso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecursoResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String tipoRecurso;
}
