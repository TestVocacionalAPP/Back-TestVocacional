package GTns_TestV.model.enums;

import java.util.*;

public enum Aptitud {
    C(Arrays.asList(2, 15, 46, 51)),
    H(Arrays.asList(30, 63, 72, 86)),
    A(Arrays.asList(22, 39, 76, 82)),
    S(Arrays.asList(4, 29, 40, 69)),
    I(Arrays.asList(10, 26, 59, 90)),
    D(Arrays.asList(13, 18, 43, 66)),
    E(Arrays.asList(7, 55, 79, 94));

    private final List<Integer> preguntas;

    Aptitud(List<Integer> preguntas) {
        this.preguntas = preguntas;
    }

    public List<Integer> getPreguntas() {
        return preguntas;
    }
}
