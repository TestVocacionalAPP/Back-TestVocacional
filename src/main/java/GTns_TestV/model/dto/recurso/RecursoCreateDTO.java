package GTns_TestV.model.dto.recurso;

import GTns_TestV.model.enums.TipoRecurso;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecursoCreateDTO {
    private String titulo;
    private String descripcion;

    @NotNull(message = "El campo tipoRecurso no puede ser nulo")
    private TipoRecurso tipoRecurso;
    private String urlRecurso;
}
