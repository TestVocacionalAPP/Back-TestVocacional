package GTns_TestV.model.dto.recurso;

import GTns_TestV.model.enums.CategoriaRecurso;
import GTns_TestV.model.enums.TipoRecurso;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecursoResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;

    @NotNull(message = "El campo tipoRecurso no puede ser nulo")
    private TipoRecurso tipoRecurso;

    @NotNull(message = "El campo categoriaRecurso no puede ser nulo")
    private CategoriaRecurso categoriaRecurso;

    private String urlRecurso;

    private boolean tieneAcceso;

    private BigDecimal precio;
}
