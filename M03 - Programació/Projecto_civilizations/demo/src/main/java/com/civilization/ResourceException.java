package com.civilization;

/**
 * Excepción personalizada para gestionar la falta de recursos en la civilización.
 */
public class ResourceException extends Exception {
    // Es buena práctica mantener el serialVersionUID para la serialización de objetos
    private static final long serialVersionUID = 2L;

    // Constructor que acepta un mensaje personalizado
    public ResourceException(String message) {
        super(message);
    }

    // Constructor por defecto con un mensaje predefinido
    public ResourceException() {
        super("No hay recursos suficientes para completar la acción.");
    }
}