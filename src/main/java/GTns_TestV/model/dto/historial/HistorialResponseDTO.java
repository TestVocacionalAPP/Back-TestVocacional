package GTns_TestV.model.dto.historial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialResponseDTO {
    private Long id;
    private Long idTest;
    private String nombreTest; // Nuevo campo para el nombre del test
    private LocalDateTime fecha;
    private String categoriaMayorInteres;
    private String categoriaMayorAptitud;
    private String mensajeIntereses;
    private String mensajeCarreras;
}
