package GTns_TestV.model.entity;

public class Resultado {
    private String nombre;
    private String tipo; // "I" para interés, "A" para aptitud

    public Resultado(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
}
