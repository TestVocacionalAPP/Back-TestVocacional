package GTns_TestV.model.enums;

import java.util.*;

public enum Interes {
    C(Arrays.asList(1, 12, 20, 53, 64, 71, 78, 85, 91, 98)),
    H(Arrays.asList(9, 25, 34, 41, 56, 67, 74, 80, 89, 95)),
    A(Arrays.asList(3, 11, 21, 28, 36, 45, 50, 57, 81, 96)),
    S(Arrays.asList(8, 16, 23, 33, 44, 52, 62, 70, 87, 92)),
    I(Arrays.asList(6, 19, 27, 38, 47, 54, 60, 75, 83, 97)),
    D(Arrays.asList(5, 14, 24, 31, 37, 48, 58, 65, 73, 84)),
    E(Arrays.asList(17, 32, 35, 42, 49, 61, 68, 77, 88, 93));

    private final List<Integer> preguntas;

    Interes(List<Integer> preguntas) {
        this.preguntas = preguntas;
    }

    public List<Integer> getPreguntas() {
        return preguntas;
    }
}
