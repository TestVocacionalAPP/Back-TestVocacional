package GTns_TestV.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AsesoriaDTO {
    private Long id;
    private UsuarioDTO usuario;   // Cambiado de usuarioId a UsuarioDTO
    private UsuarioDTO experto;   // Cambiado de expertoId a UsuarioDTO
    private String asunto;
    private LocalDateTime fechaSolicitada;
    private LocalDateTime fechaConfirmada;
}
