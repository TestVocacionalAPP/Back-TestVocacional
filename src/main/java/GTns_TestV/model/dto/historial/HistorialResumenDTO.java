package GTns_TestV.model.dto.historial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialResumenDTO {
    private Long idHistorial;
    private String resumenIntereses;
    private String resumenAptitudes;
    private LocalDateTime fecha;
}