package GTns_TestV.model.enums;

import GTns_TestV.model.entity.Resultado;

public enum ChasideCategory {
    C("Area Administrativa",
            new Resultado[]{
                    new Resultado("Organización", "I"),
                    new Resultado("Supervisión", "I"),
                    new Resultado("Orden", "I"),
                    new Resultado("Análisis y síntesis", "I"),
                    new Resultado("Colaboración", "I"),
                    new Resultado("Cálculo", "I")},
            new Resultado[]{
                    new Resultado("Persuasivo", "A"),
                    new Resultado("Objetivo", "A"),
                    new Resultado("Práctico", "A"),
                    new Resultado("Tolerante", "A"),
                    new Resultado("Responsable", "A"),
                    new Resultado("Ambicioso", "A")}),

    H("Area de Humanidades y Ciencias Sociales y Jurídicas",
            new Resultado[]{
                    new Resultado("Precisión Verbal", "I"),
                    new Resultado("Organización", "I"),
                    new Resultado("Relación de hechos", "I"),
                    new Resultado("Lingüística", "I"),
                    new Resultado("Orden", "I"),
                    new Resultado("Justicia", "I")},
            new Resultado[]{
                    new Resultado("Responsable", "A"),
                    new Resultado("Justo", "A"),
                    new Resultado("Conciliador", "A"),
                    new Resultado("Persuasivo", "A"),
                    new Resultado("Sagaz", "A"),
                    new Resultado("Imaginativo", "A")}),

    A("Area Artística",
            new Resultado[]{
                    new Resultado("Estético", "I"),
                    new Resultado("Armónico", "I"),
                    new Resultado("Manual", "I"),
                    new Resultado("Visual", "I"),
                    new Resultado("Auditivo", "I")},
            new Resultado[]{
                    new Resultado("Sensible", "A"),
                    new Resultado("Imaginativo", "A"),
                    new Resultado("Creativo", "A"),
                    new Resultado("Detallista", "A"),
                    new Resultado("Innovador", "A"),
                    new Resultado("Intuitivo", "A")}),

    S("Area de Ciencias de la Salud",
            new Resultado[]{
                    new Resultado("Asistir", "I"),
                    new Resultado("Investigar", "I"),
                    new Resultado("Precisión", "I"),
                    new Resultado("Percepción", "I"),
                    new Resultado("Análisis", "I"),
                    new Resultado("Ayudar", "I")},
            new Resultado[]{
                    new Resultado("Altruista", "A"),
                    new Resultado("Solidario", "A"),
                    new Resultado("Paciente", "A"),
                    new Resultado("Comprensivo", "A"),
                    new Resultado("Respetuoso", "A"),
                    new Resultado("Persuasivo", "A")}),

    I("Area de Enseñanzas Técnicas",
            new Resultado[]{
                    new Resultado("Cálculo", "I"),
                    new Resultado("Científico", "I"),
                    new Resultado("Manual", "I"),
                    new Resultado("Exactitud", "I"),
                    new Resultado("Planificar", "I")},
            new Resultado[]{
                    new Resultado("Preciso", "A"),
                    new Resultado("Práctico", "A"),
                    new Resultado("Crítico", "A"),
                    new Resultado("Analítico", "A"),
                    new Resultado("Rígido", "A")}),

    D("Area de Defensa y Seguridad",
            new Resultado[]{
                    new Resultado("Justicia", "I"),
                    new Resultado("Equidad", "I"),
                    new Resultado("Colaboración", "I"),
                    new Resultado("Espíritu de equipo", "I"),
                    new Resultado("Liderazgo", "I")},
            new Resultado[]{
                    new Resultado("Arriesgado", "A"),
                    new Resultado("Solidario", "A"),
                    new Resultado("Valiente", "A"),
                    new Resultado("Agresivo", "A"),
                    new Resultado("Persuasivo", "A")}),

    E("Area de Ciencias Experimentales",
            new Resultado[]{
                    new Resultado("Investigación", "I"),
                    new Resultado("Orden", "I"),
                    new Resultado("Organización", "I"),
                    new Resultado("Análisis y Síntesis", "I"),
                    new Resultado("Cálculo numérico", "I"),
                    new Resultado("Clasificar", "I")},
            new Resultado[]{
                    new Resultado("Metódico", "A"),
                    new Resultado("Analítico", "A"),
                    new Resultado("Observador", "A"),
                    new Resultado("Introvertido", "A"),
                    new Resultado("Paciente", "A"),
                    new Resultado("Seguro", "A")});

    private final String area;
    private final Resultado[] resultadosInteres;
    private final Resultado[] resultadosAptitud;

    ChasideCategory(String area, Resultado[] resultadosInteres, Resultado[] resultadosAptitud) {
        this.area = area;
        this.resultadosInteres = resultadosInteres;
        this.resultadosAptitud = resultadosAptitud;
    }

    public String getArea() {
        return area;
    }

    public Resultado[] getResultadosInteres() {
        return resultadosInteres;
    }

    public Resultado[] getResultadosAptitud() {
        return resultadosAptitud;
    }
}