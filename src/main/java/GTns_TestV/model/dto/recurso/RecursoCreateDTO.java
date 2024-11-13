package GTns_TestV.model.dto.recurso;

import GTns_TestV.model.enums.CategoriaRecurso;
import GTns_TestV.model.enums.TipoRecurso;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecursoCreateDTO {
    private String titulo;
    private String descripcion;

    @NotNull(message = "El campo tipoRecurso no puede ser nulo")
    private TipoRecurso tipoRecurso;
    private String urlRecurso;

    @NotNull(message = "El campo categoriaRecurso no puede ser nulo")
    private CategoriaRecurso categoriaRecurso;

    @NotNull(message = "El campo precio no puede ser nulo")
    private BigDecimal precio;
}
