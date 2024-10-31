package GTns_TestV.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@DiscriminatorValue("EXPERTO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Experto extends Usuario {

    private String especialidad;

    private double calificacion;

    private String descripcion;

    private double tarifa;


}
