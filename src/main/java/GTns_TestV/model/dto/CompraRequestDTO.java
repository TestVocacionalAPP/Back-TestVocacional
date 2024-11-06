package GTns_TestV.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompraRequestDTO {
    @NotNull(message = "El número de tarjeta es obligatorio")
    private String numeroTarjeta;

    @NotNull(message = "El tipo de tarjeta es obligatorio")
    private String tipoTarjeta; // Ejemplo: "Visa", "Mastercard"
}
