package GTns_TestV.model.dto;

import lombok.Data;

@Data
public class CompraResponseDTO {
    private Long id;
    private String recursoTitulo; // Producto
    private Double precio;
    private Integer cantidad;
    private String fecha;
}

