package com.civilization;

public class ResourceException extends Exception {
    private static final long serialVersionUID = 2L;

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException() {
        super("No hay recursos suficientes para completar la acción.");
    }
}