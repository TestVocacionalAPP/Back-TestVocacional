package GTns_TestV.model.dto.recurso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecursoCreateDTO {
    private String titulo;
    private String descripcion;
    private String tipoRecurso;
    private String urlRecurso;
}
