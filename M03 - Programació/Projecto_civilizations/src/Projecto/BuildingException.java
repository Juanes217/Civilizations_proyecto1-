package Projecto;

public class BuildingException extends Exception {
    private static final long serialVersionUID = 1L;

    // Constructor con mensaje personalizado
    public BuildingException(String message) {
        super(message);
    }

    // Constructor por defecto
    public BuildingException() {
        super("Faltan edificios necesarios para construir esta unidad.");
    }
}