package GTns_TestV.model.dto;

// PagoDTO.java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private String numeroTarjeta;
    private String tipoTarjeta; // Asegúrate de que este campo esté presente
}
