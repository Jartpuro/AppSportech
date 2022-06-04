package co.edu.sena.domain.enumeration;

/**
 * The StateProgram enumeration.
 */
public enum StateProgram {
    EXECUTION("Ejecución"),
    DISCONTINUED("Suspendido");

    private final String value;

    StateProgram(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
