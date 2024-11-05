package GTns_TestV.model.dto.comentario;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioResponseDTO {
    private Long id;
    private String contenido;
    private LocalDateTime fecha;
    private String nombreUsuario;
    private String apellidoUsuario;
    private Long usuarioId;
}