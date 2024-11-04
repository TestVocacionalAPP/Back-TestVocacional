package GTns_TestV.model.dto.historial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialCreateDTO {
    private Long idUsuario;
    private Long idTest;
    private LocalDateTime fecha;
    private String categoriaMayorInteres;
    private String categoriaMayorAptitud;
    private String mensajeIntereses;
    private String mensajeCarreras;
}
